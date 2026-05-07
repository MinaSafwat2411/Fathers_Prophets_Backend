package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.users.UpdateEmailRequest
import com.fathersprophets.backend.models.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.users.UpdateProfileRequest
import com.fathersprophets.backend.services.users.IUserService
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.profileRoutes(userService: IUserService) {
    route("/profile") {
        get {
            val lang = call.request.header("Accept-Language") ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()

            val user = userService.getUserById(userId, lang)
            call.respond(user)
        }

        put("/email") {
            val lang = call.request.header("Accept-Language") ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            val request = call.receive<UpdateEmailRequest>()

            val result = userService.updateEmail(userId, request, lang)
            call.respond(result)
        }

        put("/password") {
            val lang = call.request.header("Accept-Language") ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            val request = call.receive<UpdatePasswordRequest>()

            val result = userService.updatePassword(userId, request, lang)
            call.respond(result)
        }

        put("/phone") {
            val lang = call.request.header("Accept-Language") ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            val request = call.receive<UpdatePhoneRequest>()

            val result = userService.updatePhone(userId, request, lang)
            call.respond(result)
        }

        put {
            val lang = call.request.header("Accept-Language") ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            val request = call.receive<UpdateProfileRequest>()

            val result = userService.updateProfile(userId, request, lang)
            call.respond(result)
        }
    }
}
