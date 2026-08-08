package com.fathersprophets.backend.modules.family

import com.fathersprophets.backend.database.tables.family.FamilyCreateDto
import com.fathersprophets.backend.database.tables.family.FamilyUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.modules.family.service.IFamilyService
import com.fathersprophets.backend.utils.Localization
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.familyRoutes(familyService: IFamilyService) {
    route("/families") {
        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(familyService.getAll(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(familyService.getById(id, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<FamilyCreateDto>()
            call.respond(familyService.create(request, lang))
        }

        put("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            val request = call.receive<FamilyUpdateDto>()
            call.respond(familyService.update(id, request, lang))
        }

        delete("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException(Localization.get("invalid_id", lang))
            call.respond(familyService.delete(id, lang))
        }
    }
}