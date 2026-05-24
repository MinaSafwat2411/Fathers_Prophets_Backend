package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.routes.attendanceRoutes
import com.fathersprophets.backend.routes.authRoutes
import com.fathersprophets.backend.routes.classMemberRoutes
import com.fathersprophets.backend.routes.classRoutes
import com.fathersprophets.backend.routes.commentRoutes
import com.fathersprophets.backend.routes.eventMemberRoutes
import com.fathersprophets.backend.routes.eventRoutes
import com.fathersprophets.backend.routes.profileRoutes
import com.fathersprophets.backend.routes.sessionRoutes
import com.fathersprophets.backend.routes.settingRoutes
import com.fathersprophets.backend.routes.userRoutes
import com.fathersprophets.backend.services.attendance.IAttendanceService
import com.fathersprophets.backend.services.auth.IAuthService
import com.fathersprophets.backend.services.classes.IClassService
import com.fathersprophets.backend.services.classmember.IClassMemberService
import com.fathersprophets.backend.services.comments.ICommentsService
import com.fathersprophets.backend.services.eventmember.IEventMemberService
import com.fathersprophets.backend.services.events.IEventService
import com.fathersprophets.backend.services.session.ISessionService
import com.fathersprophets.backend.services.users.IUserService
import com.fathersprophets.backend.services.version.IVersionService
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    val authService = get<IAuthService>()
    val userService = get<IUserService>()
    val classService = get<IClassService>()
    val classMemberService = get<IClassMemberService>()
    val commentsService = get<ICommentsService>()
    val versionService = get<IVersionService>()
    val sessionService = get<ISessionService>()
    val attendanceService = get<IAttendanceService>()
    val eventService = get<IEventService>()
    val eventMemberService = get<IEventMemberService>()



    routing {
        route("/api/v1") {
            authRoutes(authService)
            settingRoutes(versionService)
            authenticate("auth-jwt") {
                intercept(ApplicationCallPipeline.Call) {
                    call.requireReviewed()
                }
                userRoutes(userService)
                classRoutes(classService)
                profileRoutes(userService)
                classMemberRoutes(classMemberService)
                commentRoutes(commentsService)
                sessionRoutes(sessionService)
                attendanceRoutes(attendanceService)
                eventRoutes(eventService)
                eventMemberRoutes(eventMemberService)
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
