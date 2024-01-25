package com.bitalarm.service

import com.bitalarm.dto.slack.SlackMessage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient

@Service
@Transactional
class SlackService {
    fun sendMessage(message: SlackMessage) {
        val webHookClient = WebClient.create("https://hooks.slack.com/services")
        webHookClient.post()
            .uri("/T031X6E1M1A/B031N7EHEQ6/zOuoEqp6sSjzauMFyIVmRC4I")
            .bodyValue(message)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }

    fun sendMessage(message: String) {
        sendMessage(
            SlackMessage(
                channel = "coin",
                username = "BitAlarm",
                text = message
            )
        )
    }
}