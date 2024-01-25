package com.bitalarm.dto.slack

data class SlackMessage (
    val channel: String? = null,
    val username: String? = null,
    val text: String? = null
)