package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.users.AddUserRequest
import com.fathersprophets.backend.models.users.UpdateUserRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.users.IUserService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(
    userService: IUserService
) {
    route("/users") {
        get {
            val lang = call.request.header("Accept-Language") ?: "en"
            val users = userService.getAllUsers(lang)
            call.respond(users)
        }

        get("/unreviewed") {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val users = userService.getUnReviewedUsers(lang)
            call.respond(users)
        }



        get("/birthdays/upcoming") {
            val lang = call.request.header("Accept-Language") ?: "en"
            val users = userService.getUpcomingBirthdays(lang)
            call.respond(users)
        }

        get("/role/{role}") {
            val role = call.parameters["role"] ?: ""
            val lang = call.request.header("Accept-Language") ?: "en"
            val users = userService.getUsersByRole(role, lang)
            call.respond(users)
        }

        get("/{id}") {
            val userId = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val user = userService.getUserById(userId, lang)
            call.respond(user)
        }

        post {
            call.requireRole("admin", "superadmin")
            val request = call.receive<AddUserRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = userService.addUser(request,lang)
            call.respond(result)
        }

        patch("/{id}/review") {
            call.requireRole("admin", "superadmin")
            val userId = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = userService.updateReview(userId, lang)
            call.respond(result)
        }
        put("/{id}") {
            call.requireRole("admin", "superadmin")
            val userId = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateUserRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = userService.updateUser(userId, request, lang)
            call.respond(result)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val userId = call.parameters["id"]?.toIntOrNull()
            val result = userService.deleteUser(userId,lang)
            call.respond(result)
        }
    }
}
