package com.example.core.service;

import com.example.core.component.DistributeLockExecutor;
import com.example.core.exception.CouponIssueException;
import com.example.core.repository.redis.RedisRepository;
import com.example.core.repository.redis.dto.CouponIssueRequest;
import com.example.core.repository.redis.dto.CouponRedisEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.core.exception.ErrorCode.FAIL_COUPON_ISSUE_REQUEST;
import static com.example.core.util.CouponRedisUtils.getIssueRequestKey;
import static com.example.core.util.CouponRedisUtils.getIssueRequestQueueKey;

/**
 * Redis 이용해서 lock 구현
 */
@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV1 {

    private final RedisRepository redisRepository;
    private final CouponIssueRedisService couponIssueRedisService;
    private final DistributeLockExecutor distributeLockExecutor;
    private final CouponCacheService couponCacheService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void issue(long couponId, long userId) {
        CouponRedisEntity coupon = couponCacheService.getCouponCache(couponId);
        // NOTE: 이미 소진된 경우 빠르게 막을 수 있음. 근데 추가 발급되는 경우는 억울한 케이스가 생길 순 있곘다.
        coupon.checkIssuableCoupon();
        distributeLockExecutor.execute("lock_%s".formatted(couponId), 3000, 3000, () -> {
            // NOTE: 원래는 여기가 그냥 issue service issue
            couponIssueRedisService.checkCouponIssueQuantity(coupon, userId);
            issueRequest(couponId, userId);
        });
    }

    private void issueRequest(long couponId, long userId) {
        CouponIssueRequest issueRequest = new CouponIssueRequest(couponId, userId);
        try {
            String value = objectMapper.writeValueAsString(issueRequest);
            redisRepository.sAdd(getIssueRequestKey(couponId), String.valueOf(userId));
            redisRepository.rPush(getIssueRequestQueueKey(), value);
        } catch (JsonProcessingException e) {
            throw new CouponIssueException(FAIL_COUPON_ISSUE_REQUEST, "input: %s".formatted(issueRequest));
        }
    }
}
