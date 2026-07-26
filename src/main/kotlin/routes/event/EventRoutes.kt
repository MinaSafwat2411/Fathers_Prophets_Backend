package com.fathersprophets.backend.routes.event

import com.fathersprophets.backend.models.event.CreateEventRequest
import com.fathersprophets.backend.models.event.UpdateEventRequest
import com.fathersprophets.backend.plugins.requireAdminOrType
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.events.IEventService
import com.fathersprophets.backend.utils.EventBroadcaster
import com.fathersprophets.backend.utils.receiveMultipartForm
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Route.eventRoutes(
    eventService: IEventService,
) {
    route("/events") {
        webSocket {
            try {
                val lang = call.request.header("Accept-Language") ?: "en"
                val initialResponse = eventService.getAllEvents(lang)
                sendSerialized(initialResponse)

                val job = launch {
                    EventBroadcaster.eventFlow.collectLatest { events ->
                        sendSerialized(events)
                    }
                }

                for (frame in incoming) {
                    if (frame is Frame.Close) {
                        job.cancel()
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client disconnected"))
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, e.message ?: "Unknown error"))
            }
        }

        post {
            val lang = call.request.header("Accept-Language") ?: "en"
            val form = call.receiveMultipartForm(lang)
            val request = CreateEventRequest(
                title = form.fields["title"],
                dateTime = form.fields["dateTime"],
                type = form.fields["type"],
                image = form.imageUrl
            )
            call.requireAdminOrType(request.type)
            val response = eventService.addEvent(request, lang)
            EventBroadcaster.broadcastEvents(eventService.getAllEvents(lang))
            call.respond(response)
        }
        put("/{id}") {
            val lang = call.request.header("Accept-Language") ?: "en"
            val form = call.receiveMultipartForm(lang)
            val request = UpdateEventRequest(
                title = form.fields["title"],
                dateTime = form.fields["dateTime"],
                type = form.fields["type"],
                image = form.imageUrl
            )
            call.requireAdminOrType(request.type)
            val id = call.parameters["id"]?.toIntOrNull()
            val response = eventService.updateEvent(id, request, lang)
            EventBroadcaster.broadcastEvents(eventService.getAllEvents(lang))
            call.respond(response)
        }
        delete("/{id}") {
            call.requireRole("admin", "superadmin")
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userRole = principal?.payload?.getClaim("role")?.asString()

            val response = eventService.deleteEvent(userRole, id, lang)
            EventBroadcaster.broadcastEvents(eventService.getAllEvents(lang))
            call.respond(response)
        }
        get("/count") {
            val lang = call.request.header("Accept-Language") ?: "en"
            call.respond(eventService.getEventsCount(lang))
        }
        get("/upcoming") {
            val lang = call.request.header("Accept-Language") ?: "en"
            call.respond(eventService.getUpcomingEvents(lang))
        }
    }
}
