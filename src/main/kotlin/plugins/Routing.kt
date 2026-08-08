package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.modules.auth.authRoutes
import com.fathersprophets.backend.modules.auth.service.IAuthService
import com.fathersprophets.backend.modules.classes.classRoutes
import com.fathersprophets.backend.modules.classes.service.IClassService
import com.fathersprophets.backend.modules.comments.commentRoutes
import com.fathersprophets.backend.modules.comments.service.ICommentService
import com.fathersprophets.backend.modules.family.familyRoutes
import com.fathersprophets.backend.modules.family.service.IFamilyService
import com.fathersprophets.backend.modules.profile.profileRoutes
import com.fathersprophets.backend.modules.profile.service.IProfileService
import com.fathersprophets.backend.modules.quiz.quizRoutes
import com.fathersprophets.backend.modules.quiz.service.IQuizService
import com.fathersprophets.backend.modules.quizday.quizDayRoutes
import com.fathersprophets.backend.modules.quizday.service.IQuizDayService
import com.fathersprophets.backend.modules.quizdayquestion.quizDayQuestionRoutes
import com.fathersprophets.backend.modules.quizdayquestion.service.IQuizDayQuestionService
import com.fathersprophets.backend.modules.user.service.IUserService
import com.fathersprophets.backend.modules.user.userRoutes
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
    val familyService = get<IFamilyService>()
    val userService = get<IUserService>()
    val profileService = get<IProfileService>()
    val commentService = get<ICommentService>()
    val quizService = get<IQuizService>()
    val quizDayService = get<IQuizDayService>()
    val quizDayQuestionService = get<IQuizDayQuestionService>()

    routing {
        staticFiles(FileStorage.URL_PATH, FileStorage.uploadDir)

        route("/api/v1") {
            authRoutes(authService)
            authenticate("auth-jwt") {
                install(RequireReviewedPlugin)

                classRoutes(classService)
                familyRoutes(familyService)
                userRoutes(userService)
                profileRoutes(profileService)
                commentRoutes(commentService)
                quizRoutes(quizService)
                quizDayRoutes(quizDayService)
                quizDayQuestionRoutes(quizDayQuestionService)
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
