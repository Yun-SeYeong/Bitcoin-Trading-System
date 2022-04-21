package com.demo.bitcointradingsystem.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class UpbitUtil {
    companion object {
        fun generateToken(accessKey: String, secretKey: String): String {
            val algorithm = Algorithm.HMAC256(secretKey)
            val jwtToken = JWT.create()
                    .withClaim("access_key", accessKey)
                    .withClaim("nonce", UUID.randomUUID().toString())
                    .sign(algorithm)

            return "Bearer $jwtToken"
        }
    }
}