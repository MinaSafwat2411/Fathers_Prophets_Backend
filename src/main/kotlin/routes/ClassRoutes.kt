package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.dto.classes.CreateClassRequest
import com.fathersprophets.backend.models.dto.classes.UpdateClassRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.classes.IClassService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.classRoutes(classService: IClassService) {
    route("/classes") {
        get {
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = classService.getAllClasses(lang)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/{id}") {
            val lang = call.request.header("Accept-Language") ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val result = classService.getClassById(id, lang)
            if (result.data == null) {
                call.respond(HttpStatusCode.NotFound, result)
            } else {
                call.respond(HttpStatusCode.OK, result)
            }
        }

        post {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<CreateClassRequest>()
            val result = classService.createClass(request, lang)
            call.respond(HttpStatusCode.Created, result)
        }

        put {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<UpdateClassRequest>()
            val result = classService.updateClass(request, lang)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val result = classService.deleteClass(id, lang)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}
