package com.demo.bitcointradingsystem.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.math.BigInteger
import java.security.MessageDigest
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

        fun getAuthenticationTokenWithParam(accessKey: String, secretKey: String, params: HashMap<String, String>): String {
            val md = MessageDigest.getInstance("SHA-512")
            md.update(this.getQueryString(params).toByteArray(charset("UTF-8")))
            val queryHash = String.format("%0128x", BigInteger(1, md.digest()))
            val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
            val jwtToken: String = JWT.create()
                    .withClaim("access_key", accessKey)
                    .withClaim("nonce", UUID.randomUUID().toString())
                    .withClaim("query_hash", queryHash)
                    .withClaim("query_hash_alg", "SHA512")
                    .sign(algorithm)
            return "Bearer $jwtToken"
        }

        private fun getQueryString(params: HashMap<String, String>): String {
            val queryElements = ArrayList<String>()
            for (entity: Map.Entry<String, String> in params.entries) {
                queryElements.add(entity.key + "=" + entity.value)
            }
            return queryElements.joinToString("&")
        }
    }
}