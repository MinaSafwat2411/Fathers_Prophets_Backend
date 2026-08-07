package com.fathersprophets.backend.modules.classes

import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.modules.classes.service.IClassService
import com.fathersprophets.backend.utils.Localization
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.classRoutes(classService: IClassService) {
    route("/classes") {
        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(classService.getAll(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(classService.getById(id, lang))
        }

        get("/family/{familyId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val familyId = call.parameters["familyId"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(classService.getByFamilyId(familyId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<ClassCreateDto>()
            call.respond(classService.create(request, lang))
        }

        put("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            val request = call.receive<ClassUpdateDto>()
            call.respond(classService.update(id, request, lang))
        }

        delete("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(classService.delete(id, lang))
        }
    }
}