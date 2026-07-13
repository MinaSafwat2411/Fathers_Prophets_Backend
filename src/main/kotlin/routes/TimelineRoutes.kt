package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.timeline.CreateTimelineRequest
import com.fathersprophets.backend.models.timeline.UpdateTimelineRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.activity.timeline.ITimelineService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.timelineRoutes(service: ITimelineService) {
    route("/timeline") {

        get {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            call.respond(service.getAllTimelines(lang))
        }

        get("/{id}") {
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.getTimelineById(id, lang))
        }

        post {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val request = call.receive<CreateTimelineRequest>()
            call.respond(service.createTimeline(request, lang))
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receive<UpdateTimelineRequest>()
            call.respond(service.updateTimeline(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin", "games")
            val lang = call.request.headers["Accept-Language"] ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(service.deleteTimeline(id, lang))
        }
    }
}