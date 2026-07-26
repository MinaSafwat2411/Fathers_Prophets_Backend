package com.fathersprophets.backend.routes.superevent

import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingPaymentRequest
import com.fathersprophets.backend.models.supereventbooking.SuperEventBookingRequest
import com.fathersprophets.backend.plugins.forbidRoles
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.superevent.supereventbooking.ISuperEventBookingService
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.superEventBookingRoutes(superEventBookingService: ISuperEventBookingService) {
    route("/super-event-bookings") {

        post {
            val lang = call.request.header("Accept-Language") ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            val request = call.receive<SuperEventBookingRequest>()

            call.respond(superEventBookingService.bookSeat(request.copy(userId = userId), lang))
        }

        delete("/{superEventId}") {
            val lang = call.request.header("Accept-Language") ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val callerUserId = principal?.payload?.getClaim("userId")?.asInt()
            val superEventId = call.parameters["superEventId"]?.toIntOrNull()

            val targetUserId = call.request.queryParameters["userId"]?.toIntOrNull()?.also {
                call.requireRole("admin", "superadmin")
            } ?: callerUserId

            call.respond(superEventBookingService.cancelBooking(superEventId, targetUserId, lang))
        }

        get("/event/{superEventId}") {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val superEventId = call.parameters["superEventId"]?.toIntOrNull()
            call.respond(superEventBookingService.getBookingsBySuperEventId(superEventId, lang))
        }

        get("/my-booking/{superEventId}") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim("userId")?.asInt()
            val lang = call.request.header("Accept-Language") ?: "en"
            val superEventId = call.parameters["superEventId"]?.toIntOrNull()
            call.respond(superEventBookingService.getBookingSeatByUserIdAndEventId(userId, superEventId, lang))
        }

        put("/pay/{id}") {
            call.forbidRoles("members")
            val lang = call.request.header("Accept-Language") ?: "en"
            val principal = call.principal<JWTPrincipal>()
            val request = call.receive<SuperEventBookingPaymentRequest>()
            call.respond(
                superEventBookingService.updateBookingPaidAmount(request.copy(
                    teacherId = principal?.payload?.getClaim("userId")?.asInt()
                ), lang)
            )
        }
    }
}