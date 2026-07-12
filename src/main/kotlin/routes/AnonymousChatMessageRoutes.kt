package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.anonymouschatmessage.CreateAnonymousChatMessageRequest
import com.fathersprophets.backend.models.anonymouschatmessage.UpdateAnonymousChatMessageRequest
import com.fathersprophets.backend.services.chat.anonymouschatmessage.IAnonymousChatMessageService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.anonymousChatMessageRoutes(service: IAnonymousChatMessageService) {
    route("/anonymous-chat-messages") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllMessages(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getMessageById(id, lang))
        }

        get("/chat/{chatId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val chatId = call.parameters["chatId"]?.toIntOrNull()
            call.respond(service.getMessagesByChatId(chatId, lang))
        }

        post {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateAnonymousChatMessageRequest>()
            call.respond(service.createMessage(request, lang))
        }

        put("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateAnonymousChatMessageRequest>()
            call.respond(service.updateMessage(id, request, lang))
        }

        delete("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteMessage(id, lang))
        }
    }
}