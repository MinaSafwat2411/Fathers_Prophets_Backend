package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.request.auth.LoginRequest
import com.fathersprophets.backend.models.request.auth.RegisterRequest
import com.fathersprophets.backend.services.IAuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(authService: IAuthService) {

    route("/auth") {
        post("/login") {
            val  request = call.receive<LoginRequest>()
            val result = authService.login(request)

            call.respond(
                HttpStatusCode.OK,
                result
            )
        }

        post("/register") {
            val request = call.receive<RegisterRequest>()

            val result = authService.register(request)

            call.respond(
                HttpStatusCode.Created,
                result
            )
        }

        post("/refresh-token") {
            // TODO: Implement refresh token logic in IAuthService
            call.respond(
                com.fathersprophets.backend.models.ApiResponse<Nothing>(
                    success = false,
                    message = "Not implemented"
                )
            )
        }

        post("/logout") {
            // TODO: Implement logout logic if needed (e.g., blacklisting tokens)
            call.respond(
                com.fathersprophets.backend.models.ApiResponse<Nothing>(
                    success = true,
                    message = "Logged out successfully"
                )
            )
        }
    }
}
