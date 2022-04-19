package com.demo.bitcointradingsystem.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(objectMapper: ObjectMapper): WebClient {
        val codecs = ExchangeStrategies.builder()
                .codecs {
                    it.defaultCodecs()
                            .jackson2JsonDecoder(
                                    Jackson2JsonDecoder(objectMapper.copy().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE))
                            )
                }.build()

        return WebClient.builder()
                .baseUrl("https://api.upbit.com/v1")
                .exchangeStrategies(codecs)
                .build()
    }
}