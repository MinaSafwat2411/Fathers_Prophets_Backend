package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.anonymouschat.CreateAnonymousChatRequest
import com.fathersprophets.backend.models.anonymouschat.UpdateAnonymousChatRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.anonymouschat.IAnonymousChatService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.anonymousChatRoutes(service: IAnonymousChatService) {
    route("/anonymous-chats") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllAnonymousChats(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getAnonymousChatById(id, lang))
        }

        post {
            call.requireRole("member")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateAnonymousChatRequest>()
            call.respond(service.createAnonymousChat(request, lang))
        }

        put("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateAnonymousChatRequest>()
            call.respond(service.updateAnonymousChat(id, request, lang))
        }

        delete("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteAnonymousChat(id, lang))
        }
    }
}