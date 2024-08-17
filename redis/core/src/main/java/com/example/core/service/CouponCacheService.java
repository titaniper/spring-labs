package com.example.core.service;

import com.example.core.model.Coupon;
import com.example.core.repository.redis.dto.CouponRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponCacheService {

    private final CouponIssueService couponIssueService;

    // NOTE: 메서드 호출 시 항상 메서드를 실행합니다. 주로 데이터를 업데이트하고 해당 최신 값을 캐시에 반영할 때 사용됩니다.
    @Cacheable(cacheNames = "coupon")
    public CouponRedisEntity getCouponCache(long couponId) {
        Coupon coupon = couponIssueService.findCoupon(couponId);
        return new CouponRedisEntity(coupon);
    }

    // NOTE: CachePut 항상 반환값을 캐시, cacheManager primary 로 redis 가 선언되어 있음
    @CachePut(cacheNames = "coupon")
    public CouponRedisEntity putCouponCache(long couponId) {
        return getCouponCache(couponId);
    }

    /**
     * CacheManager는 Spring의 캐싱 추상화의 일부로, 다양한 캐싱 솔루션(예: EhCache, Guava, Hazelcast, Redis 등)과의 통합을 관리합니다. CacheManager 인터페이스는 캐시의 생성, 구성, 접근, 삭제 등을 관리합니다. 개발자는 이 인터페이스를 통해 애플리케이션에서 사용할 캐시를 설정하고 관리할 수 있습니다.
     * @param couponId
     * @return
     */
    @Cacheable(cacheNames = "coupon", cacheManager = "localCacheManager")
    public CouponRedisEntity getCouponLocalCache(long couponId) {
        return proxy().getCouponCache(couponId);
    }

    @CachePut(cacheNames = "coupon", cacheManager = "localCacheManager")
    public CouponRedisEntity putCouponLocalCache(long couponId) {
        return getCouponLocalCache(couponId);
    }

    private CouponCacheService proxy() {
        return ((CouponCacheService) AopContext.currentProxy());
    }
}
