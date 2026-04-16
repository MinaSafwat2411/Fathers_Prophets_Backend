package com.fathersprophets.backend.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object JwtConfig {

    private const val secret = "your-secret-key"
    private const val issuer = "fathers-prophets"
    private const val audience = "fathers-prophets-users"

    private val algorithm = Algorithm.HMAC256(secret)

    val verifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    fun generateAccessToken(userId: Int, username: String, role: String,isReviewed: Boolean): String {
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("userId", userId)
            .withClaim("username", username)
            .withClaim("role", role)
            .withClaim("isReviewed", isReviewed)
            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
            .sign(algorithm)
    }

    fun generateRefreshToken(userId: Int): String {
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days
            .sign(algorithm)
    }
    
    fun verifyRefreshToken(token: String): Int? {
        return try {
            val decodedToken = verifier.verify(token)
            decodedToken.getClaim("userId").asInt()
        } catch (e: Exception) {
            null
        }
    }
}