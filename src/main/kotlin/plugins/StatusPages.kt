package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.ForbiddenException
import com.fathersprophets.backend.exceptions.UnauthorizedException
import com.fathersprophets.backend.models.ApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {

    install(StatusPages) {

        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse<Nothing>(
                    success = false,
                    message = cause.message ?: "Bad request"
                )
            )
        }

        exception<NoSuchElementException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                ApiResponse<Nothing>(
                    success = false,
                    message = cause.message ?: "Not found"
                )
            )
        }

        exception<UnauthorizedException> { call, cause ->
            call.respond(
                HttpStatusCode.Unauthorized,
                ApiResponse<Nothing>(
                    success = false,
                    message = cause.message ?: "Unauthorized"
                )
            )
        }

        exception<ConflictException> { call, cause ->
            call.respond(
                HttpStatusCode.Conflict,
                ApiResponse<Nothing>(
                    success = false,
                    message = cause.message ?: "Conflict"
                )
            )
        }

        exception<BadRequestException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse<Nothing>(
                    success = false,
                    message = cause.message ?: "Bad request"
                )
            )
        }

        exception<ForbiddenException> { call, cause ->
            call.respond(
                HttpStatusCode.Forbidden,
                ApiResponse<Nothing>(
                    success = false,
                    message = cause.message ?: "Forbidden"
                )
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ApiResponse<Nothing>(
                    success = false,
                    message = cause.localizedMessage ?: "Unexpected error"
                )
            )
        }
    }
}