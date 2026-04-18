package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.routes.authRoutes
import com.fathersprophets.backend.routes.classRoutes
import com.fathersprophets.backend.routes.profileRoutes
import com.fathersprophets.backend.routes.userRoutes
import com.fathersprophets.backend.services.auth.IAuthService
import com.fathersprophets.backend.services.classes.IClassService
import com.fathersprophets.backend.services.users.IUserService
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    val authService = get<IAuthService>()
    val userService = get<IUserService>()
    val classService = get<IClassService>()

    routing {
        route("/api/v1") {
            authRoutes(authService)
            authenticate("auth-jwt") {
                intercept(ApplicationCallPipeline.Call) {
                    call.requireReviewed()
                }
                userRoutes(userService)
                classRoutes(classService)
                profileRoutes(userService)
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
