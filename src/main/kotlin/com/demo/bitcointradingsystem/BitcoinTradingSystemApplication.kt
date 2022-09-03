package com.demo.bitcointradingsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class BitcoinTradingSystemApplication

fun main(args: Array<String>) {
    runApplication<BitcoinTradingSystemApplication>(*args)
}
