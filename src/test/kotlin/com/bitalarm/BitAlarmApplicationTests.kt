package com.bitalarm

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class BitAlarmApplicationTests {

    @Test
    fun contextLoads() {
        val format = LocalDateTime.now().minusDays(1)
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00"))
        println("format = $format")
    }

}
