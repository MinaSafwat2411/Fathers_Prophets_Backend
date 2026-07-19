package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.routes.chat.anonymousChatRoutes
import com.fathersprophets.backend.routes.chat.anonymousChatMessageRoutes
import com.fathersprophets.backend.routes.guessPersonAnswerRoutes
import com.fathersprophets.backend.routes.guessPersonQuestionRoutes
import com.fathersprophets.backend.routes.activity.matchpair.matchingPairRoutes
import com.fathersprophets.backend.routes.activity.matchpair.matchingPairAnswerRoutes
import com.fathersprophets.backend.routes.activity.escapeegypt.escapeEgyptRoutes
import com.fathersprophets.backend.routes.activity.escapeegypt.escapeEgyptQuestionRoutes
import com.fathersprophets.backend.routes.activity.escapeegypt.escapeEgyptAnswerRoutes
import com.fathersprophets.backend.routes.activity.timeline.timelineRoutes
import com.fathersprophets.backend.routes.activity.timeline.timelineAnswerRoutes
import com.fathersprophets.backend.routes.quizRoutes
import com.fathersprophets.backend.routes.quizAnswerRoutes
import com.fathersprophets.backend.routes.userProgressQuizRoutes
import com.fathersprophets.backend.routes.quizDayRoutes
import com.fathersprophets.backend.routes.quizDayQuestionRoutes
import com.fathersprophets.backend.routes.attendance.attendanceRoutes
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
import com.fathersprophets.backend.routes.personStoryAnswerRoutes
import com.fathersprophets.backend.routes.personRoutes
import com.fathersprophets.backend.routes.profileRoutes
import com.fathersprophets.backend.routes.attendance.sessionRoutes
import com.fathersprophets.backend.routes.settingRoutes
import com.fathersprophets.backend.routes.superEventRoutes
import com.fathersprophets.backend.routes.superEventBookingRoutes
import com.fathersprophets.backend.routes.userRoutes
import com.fathersprophets.backend.services.chat.anonymouschat.IAnonymousChatService
import com.fathersprophets.backend.services.chat.anonymouschatmessage.IAnonymousChatMessageService
import com.fathersprophets.backend.services.attendance.IAttendanceService
import com.fathersprophets.backend.services.auth.IAuthService
import com.fathersprophets.backend.services.classes.IClassService
import com.fathersprophets.backend.services.classes.classmember.IClassMemberService
import com.fathersprophets.backend.services.users.comments.ICommentsService
import com.fathersprophets.backend.services.events.eventmember.IEventMemberService
import com.fathersprophets.backend.services.events.IEventService
import com.fathersprophets.backend.services.notification.INotificationService
import com.fathersprophets.backend.services.person.IPersonService
import com.fathersprophets.backend.services.person.complete.personanswer.IPersonAnswerService
import com.fathersprophets.backend.services.person.mcq.personmcqanswer.IPersonMcqAnswerService
import com.fathersprophets.backend.services.person.personofday.IPersonOfDayService
import com.fathersprophets.backend.services.person.personstory.IPersonStoryService
import com.fathersprophets.backend.services.person.guessperson.IGuessPersonQuestionService
import com.fathersprophets.backend.services.person.guesspersonanswer.IGuessPersonAnswerService
import com.fathersprophets.backend.services.activity.matchingpair.IMatchingPairService
import com.fathersprophets.backend.services.activity.matchingpair.matchingpairanswer.IMatchingPairAnswerService
import com.fathersprophets.backend.services.activity.escapeegypt.IEscapeEgyptService
import com.fathersprophets.backend.services.activity.escapeegypt.escapeegyptquestion.IEscapeEgyptQuestionService
import com.fathersprophets.backend.services.activity.escapeegypt.escapeegyptanswer.IEscapeEgyptAnswerService
import com.fathersprophets.backend.services.activity.timeline.ITimelineService
import com.fathersprophets.backend.services.activity.timeline.timelineanswer.ITimelineAnswerService
import com.fathersprophets.backend.services.quiz.IQuizService
import com.fathersprophets.backend.services.quiz.quizanswer.IQuizAnswerService
import com.fathersprophets.backend.services.users.userprogressquiz.IUserProgressQuizService
import com.fathersprophets.backend.services.quiz.quizday.IQuizDayService
import com.fathersprophets.backend.services.quiz.quizdayquestion.IQuizDayQuestionService
import com.fathersprophets.backend.services.person.personstory.personstoryquestion.IPersonStoryQuestionService
import com.fathersprophets.backend.services.person.personstory.personstoryanswer.IPersonStoryAnswerService
import com.fathersprophets.backend.services.person.mcq.personmcq.IPersonMcqService
import com.fathersprophets.backend.services.person.complete.personquestion.IPersonQuestionService
import com.fathersprophets.backend.services.attendance.session.ISessionService
import com.fathersprophets.backend.services.superevent.ISuperEventService
import com.fathersprophets.backend.services.superevent.supereventbooking.ISuperEventBookingService
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
    val personStoryAnswerService = get<IPersonStoryAnswerService>()
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
                personStoryAnswerRoutes(personStoryAnswerService)
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
