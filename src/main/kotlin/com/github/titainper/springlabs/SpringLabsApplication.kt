package com.github.titainper.springlabs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringLabsApplication

fun main(args: Array<String>) {
    runApplication<SpringLabsApplication>(*args)
}
