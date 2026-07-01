package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.escapeegypt.CreateEscapeEgyptRequest
import com.fathersprophets.backend.models.escapeegypt.UpdateEscapeEgyptRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.escapeegypt.IEscapeEgyptService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.escapeEgyptRoutes(service: IEscapeEgyptService) {
    route("/escape-egypt") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllEscapeEgypt(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getEscapeEgyptById(id, lang))
        }

        post {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateEscapeEgyptRequest>()
            call.respond(service.createEscapeEgypt(request, lang))
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateEscapeEgyptRequest>()
            call.respond(service.updateEscapeEgypt(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteEscapeEgypt(id, lang))
        }
    }
}