package com.fathersprophets.backend.modules.auth

import com.fathersprophets.backend.modules.auth.models.ForgotPasswordRequest
import com.fathersprophets.backend.modules.auth.models.LoginRequest
import com.fathersprophets.backend.modules.auth.models.RefreshTokenRequest
import com.fathersprophets.backend.modules.auth.models.RegisterRequest
import com.fathersprophets.backend.modules.auth.models.ResendResetOtpRequest
import com.fathersprophets.backend.modules.auth.models.ResetPasswordRequest
import com.fathersprophets.backend.modules.auth.models.SendResetOtpRequest
import com.fathersprophets.backend.modules.auth.models.VerifyOtpRequest
import com.fathersprophets.backend.modules.auth.models.VerifyResetOtpRequest
import com.fathersprophets.backend.modules.auth.service.IAuthService
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(authService: IAuthService) {
    route("/auth") {
        post("/register") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<RegisterRequest>()
            call.respond(authService.register(request, lang))
        }

        post("/login") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<LoginRequest>()
            call.respond(authService.login(request, lang))
        }

        // Forgot-password flow, step 1: confirm the username exists, hand back a masked email.
        post("/forgot-password") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<ForgotPasswordRequest>()
            call.respond(authService.forgotPassword(request, lang))
        }

        // Step 2: the user types the real email back; if it matches, an OTP is emailed.
        post("/send-reset-otp") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<SendResetOtpRequest>()
            call.respond(authService.sendResetOtp(request, lang))
        }

        post("/resend-reset-otp") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<ResendResetOtpRequest>()
            call.respond(authService.resendResetOtp(request, lang))
        }

        // Step 3: verify the OTP, get back a short-lived token authorizing the password change.
        post("/verify-reset-otp") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<VerifyResetOtpRequest>()
            call.respond(authService.verifyResetOtp(request, lang))
        }

        // Step 4: submit the new password along with that token.
        post("/reset-password") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<ResetPasswordRequest>()
            call.respond(authService.resetPassword(request, lang))
        }

        authenticate("auth-jwt") {
            post("/verify-otp") {
                val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
                val lang = call.request.headers["Accept-Language"] ?: "en"

                val request = call.receive<VerifyOtpRequest>()
                call.respond(authService.verifyOtp(userId, request, lang))
            }

            post("/refresh-token") {
                val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
                val lang = call.request.headers["Accept-Language"] ?: "en"

                val request = call.receive<RefreshTokenRequest>()
                call.respond(authService.refreshToken(userId, request.refreshToken, lang))
            }

            post("/logout") {
                val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
                val lang = call.request.headers["Accept-Language"] ?: "en"

                call.respond(authService.logout(userId, lang))
            }
        }
    }
}