package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.utils.JwtConfig.verifier
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuth() {

    val jwtRealm = environment.config.propertyOrNull("jwt.realm")
        ?.getString() ?: "Access to 'fathers-prophets'"

    install(Authentication) {

        jwt("auth-jwt") {

            realm = jwtRealm

            verifier(verifier)

            validate { credential ->

                val username = credential.payload
                    .getClaim("username")
                    .asString()

                if (username.isNullOrEmpty()) {
                    null
                } else {
                    JWTPrincipal(credential.payload)
                }
            }

            challenge { _, _ ->
                call.respond(
                    io.ktor.http.HttpStatusCode.Unauthorized,
                    mapOf(
                        "success" to false,
                        "message" to "Invalid or expired token"
                    )
                )
            }
        }
    }
}
