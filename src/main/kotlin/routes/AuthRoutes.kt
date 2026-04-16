package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.request.auth.LoginRequest
import com.fathersprophets.backend.models.request.auth.RegisterRequest
import com.fathersprophets.backend.services.IAuthService
import com.fathersprophets.backend.utils.Localization
import io.ktor.http.HttpStatusCode
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
            val lang = call.request.header("Accept-Language") ?: "en"
            // TODO: Implement refresh token logic in IAuthService
            call.respond(
                com.fathersprophets.backend.models.ApiResponse<Nothing>(
                    success = false,
                    message = Localization.get("not_implemented", lang)
                )
            )
        }

        post("/logout") {
            val lang = call.request.header("Accept-Language") ?: "en"
            // TODO: Implement logout logic if needed (e.g., blacklisting tokens)
            call.respond(
                com.fathersprophets.backend.models.ApiResponse<Nothing>(
                    success = true,
                    message = Localization.get("logout_success", lang)
                )
            )
        }
    }
}
