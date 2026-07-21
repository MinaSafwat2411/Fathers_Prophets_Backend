package com.fathersprophets.backend.routes

import com.fathersprophets.backend.exceptions.UnauthorizedException
import com.fathersprophets.backend.models.auth.LoginRequest
import com.fathersprophets.backend.models.auth.RefreshRequest
import com.fathersprophets.backend.models.auth.RegisterRequest
import com.fathersprophets.backend.models.auth.ForgotPasswordRequest
import com.fathersprophets.backend.models.auth.SendOtpRequest
import com.fathersprophets.backend.models.auth.ResendOtpRequest
import com.fathersprophets.backend.models.auth.VerifyOtpRequest
import com.fathersprophets.backend.models.auth.ResetPasswordRequest
import com.fathersprophets.backend.services.auth.IAuthService
import com.fathersprophets.backend.utils.Localization
import io.ktor.http.*
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

        post("/forgot-password") {
            val request = call.receive<ForgotPasswordRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = authService.forgotPassword(request, lang)

            call.respond(
                HttpStatusCode.OK,
                result
            )
        }

        post("/forgot-password/send-otp") {
            val request = call.receive<SendOtpRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = authService.sendOtp(request, lang)

            call.respond(
                HttpStatusCode.OK,
                result
            )
        }

        post("/forgot-password/resend-otp") {
            val request = call.receive<ResendOtpRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = authService.resendOtp(request, lang)

            call.respond(
                HttpStatusCode.OK,
                result
            )
        }

        post("/forgot-password/verify-otp") {
            val request = call.receive<VerifyOtpRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = authService.verifyOtp(request, lang)

            call.respond(
                HttpStatusCode.OK,
                result
            )
        }

        post("/reset-password") {
            val request = call.receive<ResetPasswordRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = authService.resetPassword(request, lang)

            call.respond(
                HttpStatusCode.OK,
                result
            )
        }

        authenticate("auth-jwt") {
            post("/logout") {
                val principal = call.principal<JWTPrincipal>()
                val lang = call.request.header("Accept-Language") ?: "en"

                val userId = principal?.payload?.getClaim("userId")?.asInt()
                    ?: throw UnauthorizedException(Localization.get("user_need_to_login", lang))

                val result = authService.logout(userId, lang)

                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}
