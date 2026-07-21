package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.superevent.SuperEventRequest
import com.fathersprophets.backend.models.superevent.SuperEventTeacher
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.superevent.ISuperEventService
import com.fathersprophets.backend.utils.MultipartForm
import com.fathersprophets.backend.utils.receiveMultipartForm
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

private fun MultipartForm.toSuperEventRequest() = SuperEventRequest(
    title = fields["title"],
    description = fields["description"],
    location = fields["location"],
    startDate = fields["startDate"],
    endDate = fields["endDate"],
    lastBookingDate = fields["lastBookingDate"],
    totalSeats = fields["totalSeats"]?.toIntOrNull(),
    waitingListLimit = fields["waitingListLimit"]?.toIntOrNull(),
    image = base64Image,
    teachers = fields["teachers"]?.let { Json.decodeFromString<List<SuperEventTeacher>>(it) }
)

fun Route.superEventRoutes(superEventService: ISuperEventService) {
    route("/super-events") {

        get {
            val lang = call.request.header("Accept-Language") ?: "en"
            call.respond(superEventService.getAllSuperEvents(lang))
        }

        get("/upcoming") {
            val lang = call.request.header("Accept-Language") ?: "en"
            call.respond(superEventService.getUpcomingSuperEvents(lang))
        }

        get("/availability/{id}") {
            val lang = call.request.header("Accept-Language") ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(superEventService.getSuperEventAvailability(id, lang))
        }

        post {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receiveMultipartForm(lang).toSuperEventRequest()
            call.respond(superEventService.createSuperEvent(request, lang))
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            val request = call.receiveMultipartForm(lang).toSuperEventRequest()
            call.respond(superEventService.updateSuperEvent(id, request, lang))
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(superEventService.deleteSuperEvent(id, lang))
        }
    }
}