package com.fathersprophets.backend.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuth() {
    val jwtSecret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "secret"
    val jwtIssuer = environment.config.propertyOrNull("jwt.issuer")?.getString() ?: "http://0.0.0.0:8080/"
    val jwtAudience = environment.config.propertyOrNull("jwt.audience")?.getString() ?: "http://0.0.0.0:8080/"
    val jwtRealm = environment.config.propertyOrNull("jwt.realm")?.getString() ?: "Access to 'fathers-prophets'"

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
