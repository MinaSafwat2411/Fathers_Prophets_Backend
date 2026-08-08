package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.ForbiddenException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.exceptions.TooManyRequestsException
import com.fathersprophets.backend.exceptions.UnauthorizedException
import com.fathersprophets.backend.utils.Localization
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

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

        exception<NotFoundException> { call, cause ->
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

        exception<TooManyRequestsException> { call, cause ->
            call.respond(
                HttpStatusCode.TooManyRequests,
                ApiResponse<Nothing>(
                    success = false,
                    message = cause.message ?: "Too many requests"
                )
            )
        }

        exception<ExposedSQLException> { call, cause ->
            val lang = call.request.header("Accept-Language") ?: "en"
            if (cause.sqlState == "23505") {
                call.respond(
                    HttpStatusCode.Conflict,
                    ApiResponse<Nothing>(
                        success = false,
                        message = Localization.get("duplicate_value", lang)
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ApiResponse<Nothing>(
                        success = false,
                        message = Localization.get("database_error", lang)
                    )
                )
            }
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