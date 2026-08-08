package com.fathersprophets.backend.modules.user

import com.fathersprophets.backend.database.tables.user.UserCreateDto
import com.fathersprophets.backend.database.tables.user.UserUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.modules.user.service.IUserService
import com.fathersprophets.backend.utils.Localization
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(userService: IUserService) {
    route("/users") {
        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(userService.getAll(lang))
        }

        get("/birthdays") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(userService.getUsersWithBirthDate(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(userService.getById(id, lang))
        }

        get("/username/{username}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(userService.getByUsername(call.parameters["username"].orEmpty(), lang))
        }

        get("/email/{email}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(userService.getByEmail(call.parameters["email"].orEmpty(), lang))
        }

        get("/phone/{phone}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(userService.getByPhone(call.parameters["phone"].orEmpty(), lang))
        }

        get("/member/{memberId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(userService.getByMemberId(call.parameters["memberId"].orEmpty(), lang))
        }

        get("/family/{familyId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val familyId = call.parameters["familyId"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(userService.getByFamilyId(familyId, lang))
        }

        get("/class/{classId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val classId = call.parameters["classId"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(userService.getByClassId(classId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<UserCreateDto>()
            call.respond(userService.create(request, lang))
        }

        put("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            val request = call.receive<UserUpdateDto>()
            call.respond(userService.update(id, request, lang))
        }

        delete("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(userService.delete(id, lang))
        }
    }
}