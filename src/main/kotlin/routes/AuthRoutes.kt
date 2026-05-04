package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.dto.auth.LoginRequest
import com.fathersprophets.backend.models.dto.auth.RefreshRequest
import com.fathersprophets.backend.models.dto.auth.RegisterRequest
import com.fathersprophets.backend.services.auth.IAuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(authService: IAuthService) {

    route("/auth") {
        post("/login") {
            val request = call.receive<LoginRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = authService.login(request, lang)

            call.respond(
                HttpStatusCode.OK,
                result
            )
        }

        post("/register") {
            val request = call.receive<RegisterRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            
            val result = authService.register(request, lang)

            call.respond(
                HttpStatusCode.Created,
                result
            )
        }

        post("/refresh-token") {
            val request = call.receive<RefreshRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = authService.refreshToken(request, lang)

            call.respond(
                HttpStatusCode.OK,
                result
            )
        }

        authenticate("auth-jwt") {
            post("/logout") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asInt()
                    ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val lang = call.request.header("Accept-Language") ?: "en"
                val result = authService.logout(userId, lang)

                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}
