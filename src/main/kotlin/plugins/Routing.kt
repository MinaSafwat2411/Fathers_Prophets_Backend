package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.routes.authRoutes
import com.fathersprophets.backend.services.IAuthService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    val authService = get<IAuthService>()

    routing {
        route("/api/v1") {
            authRoutes(authService)
        }
    }
}
