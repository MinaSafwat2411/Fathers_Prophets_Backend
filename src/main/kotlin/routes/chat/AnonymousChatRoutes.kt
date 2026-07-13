package com.fathersprophets.backend.routes.chat

import com.fathersprophets.backend.exceptions.ForbiddenException
import com.fathersprophets.backend.models.anonymouschat.CreateAnonymousChatRequest
import com.fathersprophets.backend.models.anonymouschat.UpdateAnonymousChatRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.chat.anonymouschat.IAnonymousChatService
import com.fathersprophets.backend.utils.Localization
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.anonymousChatRoutes(service: IAnonymousChatService) {
    route("/anonymous-chats") {

        get {
            call.requireRole("superadmin", "admin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllAnonymousChats(lang))
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
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteAnonymousChat(id, lang))
        }

        get("/my-chats") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val payload = call.principal<JWTPrincipal>()
            val userId = payload?.payload?.getClaim("userId")?.asInt()
            val role = payload?.payload?.getClaim("role")?.asString()
            if (role == "member") {
                call.respond(service.getMemberChat(userId, lang))
            } else if (role.isNullOrEmpty()) {
                throw ForbiddenException(Localization.get("role_required", lang))
            } else {
                call.respond(service.getServantChat(userId, lang))
            }
        }
    }
}