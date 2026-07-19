package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.userprogressquiz.CreateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UpdateUserProgressQuizRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.users.userprogressquiz.IUserProgressQuizService
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userProgressQuizRoutes(service: IUserProgressQuizService) {
    route("/user-progress-quiz") {

        get {
            call.requireRole("superadmin", "admin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllUserProgress(lang))
        }

        get("/user/{userId}") {
            call.requireRole("superadmin", "admin", "quiz")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val userId = call.parameters["userId"]?.toIntOrNull()
            call.respond(service.getUserProgressByUserId(userId, lang))
        }


        get("/my-progress") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            call.respond(service.getUserProgressByUserId(userId, lang))
        }

        post {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateUserProgressQuizRequest>()
            call.respond(service.createUserProgress(request, lang))
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateUserProgressQuizRequest>()
            call.respond(service.updateUserProgress(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteUserProgress(id, lang))
        }
    }
}