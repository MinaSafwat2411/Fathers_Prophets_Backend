package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.notification.CreateNotificationRequest
import com.fathersprophets.backend.models.notification.UpdateNotificationRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.notification.INotificationService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.notificationRoutes(service: INotificationService) {
    route("/notifications") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllNotifications(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getNotificationById(id, lang))
        }

        get("/event/{eventId}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val eventId = call.parameters["eventId"]?.toIntOrNull()
            call.respond(service.getNotificationsByEventId(eventId, lang))
        }

        post {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateNotificationRequest>()
            call.respond(service.createNotification(request, lang))
        }

        put("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateNotificationRequest>()
            call.respond(service.updateNotification(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("superadmin")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteNotification(id, lang))
        }
    }
}