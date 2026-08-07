package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.modules.auth.authRoutes
import com.fathersprophets.backend.modules.auth.service.IAuthService
import com.fathersprophets.backend.modules.classes.classRoutes
import com.fathersprophets.backend.modules.classes.service.IClassService
import com.fathersprophets.backend.utils.FileStorage
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.http.content.staticFiles
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    val authService = get<IAuthService>()
    val classService = get<IClassService>()

    routing {
        staticFiles(FileStorage.URL_PATH, FileStorage.uploadDir)

        route("/api/v1") {
            authRoutes(authService)
            authenticate("auth-jwt") {
                install(RequireReviewedPlugin)

                classRoutes(classService)
            }
            
            get("/healthcheck") {
                call.respond(
                    ApiResponse(
                        success = true,
                        message = "Server is healthy",
                        data = "OK"
                    )
                )
            }
        }
    }
}
