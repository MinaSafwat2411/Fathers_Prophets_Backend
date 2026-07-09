package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.routes.anonymousChatRoutes
import com.fathersprophets.backend.routes.anonymousChatMessageRoutes
import com.fathersprophets.backend.routes.guessPersonAnswerRoutes
import com.fathersprophets.backend.routes.guessPersonQuestionRoutes
import com.fathersprophets.backend.routes.matchingPairRoutes
import com.fathersprophets.backend.routes.matchingPairAnswerRoutes
import com.fathersprophets.backend.routes.escapeEgyptRoutes
import com.fathersprophets.backend.routes.escapeEgyptQuestionRoutes
import com.fathersprophets.backend.routes.escapeEgyptAnswerRoutes
import com.fathersprophets.backend.routes.timelineRoutes
import com.fathersprophets.backend.routes.timelineAnswerRoutes
import com.fathersprophets.backend.routes.quizRoutes
import com.fathersprophets.backend.routes.quizAnswerRoutes
import com.fathersprophets.backend.routes.userProgressQuizRoutes
import com.fathersprophets.backend.routes.quizDayRoutes
import com.fathersprophets.backend.routes.quizDayQuestionRoutes
import com.fathersprophets.backend.routes.attendanceRoutes
import com.fathersprophets.backend.routes.authRoutes
import com.fathersprophets.backend.routes.classMemberRoutes
import com.fathersprophets.backend.routes.classRoutes
import com.fathersprophets.backend.routes.commentRoutes
import com.fathersprophets.backend.routes.eventMemberRoutes
import com.fathersprophets.backend.routes.eventRoutes
import com.fathersprophets.backend.routes.notificationRoutes
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
import com.fathersprophets.backend.routes.superEventRoutes
import com.fathersprophets.backend.routes.superEventBookingRoutes
import com.fathersprophets.backend.routes.userRoutes
import com.fathersprophets.backend.services.anonymouschat.IAnonymousChatService
import com.fathersprophets.backend.services.anonymouschatmessage.IAnonymousChatMessageService
import com.fathersprophets.backend.services.attendance.IAttendanceService
import com.fathersprophets.backend.services.auth.IAuthService
import com.fathersprophets.backend.services.classes.IClassService
import com.fathersprophets.backend.services.classmember.IClassMemberService
import com.fathersprophets.backend.services.comments.ICommentsService
import com.fathersprophets.backend.services.eventmember.IEventMemberService
import com.fathersprophets.backend.services.events.IEventService
import com.fathersprophets.backend.services.notification.INotificationService
import com.fathersprophets.backend.services.person.IPersonService
import com.fathersprophets.backend.services.personanswer.IPersonAnswerService
import com.fathersprophets.backend.services.personmcqanswer.IPersonMcqAnswerService
import com.fathersprophets.backend.services.personofday.IPersonOfDayService
import com.fathersprophets.backend.services.personstory.IPersonStoryService
import com.fathersprophets.backend.services.guessperson.IGuessPersonQuestionService
import com.fathersprophets.backend.services.guesspersonanswer.IGuessPersonAnswerService
import com.fathersprophets.backend.services.matchingpair.IMatchingPairService
import com.fathersprophets.backend.services.matchingpairanswer.IMatchingPairAnswerService
import com.fathersprophets.backend.services.escapeegypt.IEscapeEgyptService
import com.fathersprophets.backend.services.escapeegyptquestion.IEscapeEgyptQuestionService
import com.fathersprophets.backend.services.escapeegyptanswer.IEscapeEgyptAnswerService
import com.fathersprophets.backend.services.timeline.ITimelineService
import com.fathersprophets.backend.services.timelineanswer.ITimelineAnswerService
import com.fathersprophets.backend.services.quiz.IQuizService
import com.fathersprophets.backend.services.quizanswer.IQuizAnswerService
import com.fathersprophets.backend.services.userprogressquiz.IUserProgressQuizService
import com.fathersprophets.backend.services.quizday.IQuizDayService
import com.fathersprophets.backend.services.quizdayquestion.IQuizDayQuestionService
import com.fathersprophets.backend.services.personstoryquestion.IPersonStoryQuestionService
import com.fathersprophets.backend.services.personmcq.IPersonMcqService
import com.fathersprophets.backend.services.personquestion.IPersonQuestionService
import com.fathersprophets.backend.services.session.ISessionService
import com.fathersprophets.backend.services.superevent.ISuperEventService
import com.fathersprophets.backend.services.supereventbooking.ISuperEventBookingService
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
    val notificationService = get<INotificationService>()
    val personService = get<IPersonService>()
    val personQuestionService = get<IPersonQuestionService>()
    val personMcqService = get<IPersonMcqService>()
    val personAnswerService = get<IPersonAnswerService>()
    val personMcqAnswerService = get<IPersonMcqAnswerService>()
    val personOfDayService = get<IPersonOfDayService>()
    val personStoryService = get<IPersonStoryService>()
    val personStoryQuestionService = get<IPersonStoryQuestionService>()
    val guessPersonQuestionService = get<IGuessPersonQuestionService>()
    val guessPersonAnswerService = get<IGuessPersonAnswerService>()
    val matchingPairService = get<IMatchingPairService>()
    val matchingPairAnswerService = get<IMatchingPairAnswerService>()
    val escapeEgyptService = get<IEscapeEgyptService>()
    val escapeEgyptQuestionService = get<IEscapeEgyptQuestionService>()
    val escapeEgyptAnswerService = get<IEscapeEgyptAnswerService>()
    val timelineService = get<ITimelineService>()
    val timelineAnswerService = get<ITimelineAnswerService>()
    val quizService = get<IQuizService>()
    val quizAnswerService = get<IQuizAnswerService>()
    val userProgressQuizService = get<IUserProgressQuizService>()
    val quizDayService = get<IQuizDayService>()
    val quizDayQuestionService = get<IQuizDayQuestionService>()
    val anonymousChatService = get<IAnonymousChatService>()
    val anonymousChatMessageService = get<IAnonymousChatMessageService>()
    val superEventService = get<ISuperEventService>()
    val superEventBookingService = get<ISuperEventBookingService>()

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
                notificationRoutes(notificationService)
                personRoutes(personService)
                personQuestionRoutes(personQuestionService)
                personMcqRoutes(personMcqService)
                personAnswerRoutes(personAnswerService)
                personMcqAnswerRoutes(personMcqAnswerService)
                personOfDayRoutes(personOfDayService)
                personStoryRoutes(personStoryService)
                personStoryQuestionRoutes(personStoryQuestionService)
                guessPersonQuestionRoutes(guessPersonQuestionService)
                guessPersonAnswerRoutes(guessPersonAnswerService)
                matchingPairRoutes(matchingPairService)
                matchingPairAnswerRoutes(matchingPairAnswerService)
                escapeEgyptRoutes(escapeEgyptService)
                escapeEgyptQuestionRoutes(escapeEgyptQuestionService)
                escapeEgyptAnswerRoutes(escapeEgyptAnswerService)
                timelineRoutes(timelineService)
                timelineAnswerRoutes(timelineAnswerService)
                quizRoutes(quizService)
                quizAnswerRoutes(quizAnswerService)
                userProgressQuizRoutes(userProgressQuizService)
                quizDayRoutes(quizDayService)
                quizDayQuestionRoutes(quizDayQuestionService)
                anonymousChatRoutes(anonymousChatService)
                anonymousChatMessageRoutes(anonymousChatMessageService)
                superEventRoutes(superEventService)
                superEventBookingRoutes(superEventBookingService)
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
