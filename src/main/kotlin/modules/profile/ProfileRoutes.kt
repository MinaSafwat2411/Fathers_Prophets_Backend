package com.fathersprophets.backend.modules.profile

import com.fathersprophets.backend.modules.profile.models.ChangeEmailRequest
import com.fathersprophets.backend.modules.profile.models.ChangePasswordRequest
import com.fathersprophets.backend.modules.profile.models.ChangePhoneRequest
import com.fathersprophets.backend.modules.profile.models.VerifyContactOtpRequest
import com.fathersprophets.backend.modules.profile.service.IProfileService
import com.fathersprophets.backend.utils.receiveMultipartForm
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.profileRoutes(profileService: IProfileService) {
    route("/profile") {
        get {
            val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(profileService.getProfile(userId, lang))
        }

        // Email change, step 1: sends the OTP to the new address.
        post("/email/request-otp") {
            val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
            val lang = call.request.headers["Accept-Language"] ?: "en"

            val request = call.receive<ChangeEmailRequest>()
            call.respond(profileService.requestEmailChange(userId, request, lang))
        }

        // Step 2: the address itself comes from the stored OTP row, so only the code is sent here.
        post("/email/verify-otp") {
            val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
            val lang = call.request.headers["Accept-Language"] ?: "en"

            val request = call.receive<VerifyContactOtpRequest>()
            call.respond(profileService.verifyEmailChange(userId, request, lang))
        }

        // Phone change, step 1: delivers the OTP over WhatsApp.
        post("/phone/request-otp") {
            val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
            val lang = call.request.headers["Accept-Language"] ?: "en"

            val request = call.receive<ChangePhoneRequest>()
            call.respond(profileService.requestPhoneChange(userId, request, lang))
        }

        post("/phone/verify-otp") {
            val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
            val lang = call.request.headers["Accept-Language"] ?: "en"

            val request = call.receive<VerifyContactOtpRequest>()
            call.respond(profileService.verifyPhoneChange(userId, request, lang))
        }

        put("/image") {
            val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
            val lang = call.request.headers["Accept-Language"] ?: "en"

            val form = call.receiveMultipartForm(lang)
            call.respond(profileService.changeProfileImage(userId, form.imageUrl, lang))
        }

        put("/password") {
            val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()
            val lang = call.request.headers["Accept-Language"] ?: "en"

            val request = call.receive<ChangePasswordRequest>()
            call.respond(profileService.changePassword(userId, request, lang))
        }
    }
}