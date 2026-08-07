package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.modules.token.TokenDao
import com.fathersprophets.backend.utils.JwtConfig.verifier
import com.fathersprophets.backend.utils.Localization
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.get

fun Application.configureAuth() {

    val jwtRealm = environment.config.propertyOrNull("jwt.realm")
        ?.getString() ?: "Access to 'fathers-prophets'"

    val tokenDao = get<TokenDao>()

    install(Authentication) {

        jwt("auth-jwt") {

            realm = jwtRealm

            verifier(verifier)

            validate { credential ->
                val username = credential.payload.getClaim("username").asString()
                val userId = credential.payload.getClaim("userId").asInt()
                val presentedToken = request.headers["Authorization"]?.removePrefix("Bearer ")?.trim()

                if (username.isNullOrEmpty() || userId == null || presentedToken.isNullOrEmpty()) {
                    null
                } else {
                    val storedToken = tokenDao.getByUserId(userId).firstOrNull()?.token
                    if (storedToken != null && storedToken == presentedToken) {
                        JWTPrincipal(credential.payload)
                    } else {
                        null
                    }
                }
            }

            challenge { _, _ ->
                val lang = call.request.headers["Accept-Language"] ?: "en"
                call.respond(
                    HttpStatusCode.Unauthorized,
                    ApiResponse<Nothing>(
                        success = false,
                        message = Localization.get("invalid_token", lang)
                    )
                )
            }
        }
    }
}
