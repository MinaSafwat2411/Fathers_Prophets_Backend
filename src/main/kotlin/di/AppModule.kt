package com.fathersprophets.backend.di

import com.fathersprophets.backend.modules.auth.repository.AuthRepository
import com.fathersprophets.backend.modules.auth.service.AuthService
import com.fathersprophets.backend.modules.auth.service.IAuthService
import com.fathersprophets.backend.database.tables.classes.ClassDao
import com.fathersprophets.backend.modules.classes.repository.ClassRepository
import com.fathersprophets.backend.modules.classes.service.ClassService
import com.fathersprophets.backend.modules.classes.service.IClassService
import com.fathersprophets.backend.database.tables.comments.CommentDao
import com.fathersprophets.backend.modules.comments.repository.CommentRepository
import com.fathersprophets.backend.modules.comments.service.CommentService
import com.fathersprophets.backend.modules.comments.service.ICommentService
import com.fathersprophets.backend.database.tables.family.FamilyDao
import com.fathersprophets.backend.modules.family.repository.FamilyRepository
import com.fathersprophets.backend.modules.family.service.FamilyService
import com.fathersprophets.backend.modules.family.service.IFamilyService
import com.fathersprophets.backend.modules.notification.BirthdayReminderScheduler
import com.fathersprophets.backend.modules.notification.FirebaseMessagingService
import com.fathersprophets.backend.modules.notification.IFirebaseMessagingService
import com.fathersprophets.backend.modules.otp.OtpDao
import com.fathersprophets.backend.database.tables.quiz.QuizDao
import com.fathersprophets.backend.modules.quiz.repository.QuizRepository
import com.fathersprophets.backend.modules.quiz.service.IQuizService
import com.fathersprophets.backend.modules.quiz.service.QuizService
import com.fathersprophets.backend.database.tables.quizday.QuizDayDao
import com.fathersprophets.backend.modules.quizday.repository.QuizDayRepository
import com.fathersprophets.backend.modules.quizday.service.IQuizDayService
import com.fathersprophets.backend.modules.quizday.service.QuizDayService
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionsDao
import com.fathersprophets.backend.modules.quizdayquestion.repository.QuizDayQuestionRepository
import com.fathersprophets.backend.modules.quizdayquestion.service.IQuizDayQuestionService
import com.fathersprophets.backend.modules.quizdayquestion.service.QuizDayQuestionService
import com.fathersprophets.backend.modules.profile.repository.ProfileRepository
import com.fathersprophets.backend.modules.profile.service.IProfileService
import com.fathersprophets.backend.modules.profile.service.ProfileService
import com.fathersprophets.backend.modules.token.TokenDao
import com.fathersprophets.backend.database.tables.user.UserDao
import com.fathersprophets.backend.modules.user.repository.UserRepository
import com.fathersprophets.backend.modules.user.service.IUserService
import com.fathersprophets.backend.modules.user.service.UserService
import org.koin.dsl.module

val appModule = module {
    single { UserDao() }
    single { TokenDao() }
    single { OtpDao() }
    single { ClassDao() }
    single { FamilyDao() }
    single { CommentDao() }
    single { QuizDao() }
    single { QuizDayDao() }
    single { QuizDayQuestionsDao() }

    single { AuthRepository(get(), get(), get()) }
    single<IAuthService> { AuthService(get()) }

    single { ClassRepository(get()) }
    single<IClassService> { ClassService(get()) }

    single { FamilyRepository(get()) }
    single<IFamilyService> { FamilyService(get()) }

    single { UserRepository(get()) }
    single<IUserService> { UserService(get()) }

    single { ProfileRepository(get(), get()) }
    single<IProfileService> { ProfileService(get()) }

    single { CommentRepository(get()) }
    single<ICommentService> { CommentService(get()) }

    single { QuizRepository(get()) }
    single<IQuizService> { QuizService(get()) }

    single { QuizDayRepository(get()) }
    single<IQuizDayService> { QuizDayService(get()) }

    single { QuizDayQuestionRepository(get()) }
    single<IQuizDayQuestionService> { QuizDayQuestionService(get()) }

    single<IFirebaseMessagingService> { FirebaseMessagingService() }
    single { BirthdayReminderScheduler(get(), get(), get()) }
}