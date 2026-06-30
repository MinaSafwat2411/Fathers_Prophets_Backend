package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.routes.guessPersonQuestionRoutes
import com.fathersprophets.backend.routes.attendanceRoutes
import com.fathersprophets.backend.routes.authRoutes
import com.fathersprophets.backend.routes.classMemberRoutes
import com.fathersprophets.backend.routes.classRoutes
import com.fathersprophets.backend.routes.commentRoutes
import com.fathersprophets.backend.routes.eventMemberRoutes
import com.fathersprophets.backend.routes.eventRoutes
import com.fathersprophets.backend.routes.personAnswerRoutes
import com.fathersprophets.backend.routes.personMcqAnswerRoutes
import com.fathersprophets.backend.routes.personMcqRoutes
import com.fathersprophets.backend.routes.personOfDayRoutes
import com.fathersprophets.backend.routes.personQuestionRoutes
import com.fathersprophets.backend.routes.personStoryRoutes
import com.fathersprophets.backend.routes.personStoryQuestionRoutes
import com.fathersprophets.backend.routes.personRoutes
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
import com.fathersprophets.backend.services.person.IPersonService
import com.fathersprophets.backend.services.personanswer.IPersonAnswerService
import com.fathersprophets.backend.services.personmcqanswer.IPersonMcqAnswerService
import com.fathersprophets.backend.services.personofday.IPersonOfDayService
import com.fathersprophets.backend.services.personstory.IPersonStoryService
import com.fathersprophets.backend.services.guessperson.IGuessPersonQuestionService
import com.fathersprophets.backend.services.personstoryquestion.IPersonStoryQuestionService
import com.fathersprophets.backend.services.personmcq.IPersonMcqService
import com.fathersprophets.backend.services.personquestion.IPersonQuestionService
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
    val personService = get<IPersonService>()
    val personQuestionService = get<IPersonQuestionService>()
    val personMcqService = get<IPersonMcqService>()
    val personAnswerService = get<IPersonAnswerService>()
    val personMcqAnswerService = get<IPersonMcqAnswerService>()
    val personOfDayService = get<IPersonOfDayService>()
    val personStoryService = get<IPersonStoryService>()
    val personStoryQuestionService = get<IPersonStoryQuestionService>()
    val guessPersonQuestionService = get<IGuessPersonQuestionService>()

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
                personRoutes(personService)
                personQuestionRoutes(personQuestionService)
                personMcqRoutes(personMcqService)
                personAnswerRoutes(personAnswerService)
                personMcqAnswerRoutes(personMcqAnswerService)
                personOfDayRoutes(personOfDayService)
                personStoryRoutes(personStoryService)
                personStoryQuestionRoutes(personStoryQuestionService)
                guessPersonQuestionRoutes(guessPersonQuestionService)
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
