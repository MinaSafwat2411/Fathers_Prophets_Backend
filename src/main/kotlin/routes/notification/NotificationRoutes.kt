package com.fathersprophets.backend.routes.notification

import com.fathersprophets.backend.services.notification.INotificationService
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.notificationRoutes(service: INotificationService) {
    route("/notifications") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllNotifications(lang))
        }
    }
}