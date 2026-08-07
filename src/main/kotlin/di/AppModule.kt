package com.fathersprophets.backend.di

import com.fathersprophets.backend.modules.auth.repository.AuthRepository
import com.fathersprophets.backend.modules.auth.service.AuthService
import com.fathersprophets.backend.modules.auth.service.IAuthService
import com.fathersprophets.backend.modules.classes.ClassDao
import com.fathersprophets.backend.modules.classes.repository.ClassRepository
import com.fathersprophets.backend.modules.classes.service.ClassService
import com.fathersprophets.backend.modules.classes.service.IClassService
import com.fathersprophets.backend.modules.notification.BirthdayReminderScheduler
import com.fathersprophets.backend.modules.notification.FirebaseMessagingService
import com.fathersprophets.backend.modules.notification.IFirebaseMessagingService
import com.fathersprophets.backend.modules.otp.OtpDao
import com.fathersprophets.backend.modules.token.TokenDao
import com.fathersprophets.backend.modules.user.UserDao
import org.koin.dsl.module

val appModule = module {
    single { UserDao() }
    single { TokenDao() }
    single { OtpDao() }
    single { ClassDao() }

    single { AuthRepository(get(), get(), get()) }
    single<IAuthService> { AuthService(get()) }

    single { ClassRepository(get()) }
    single<IClassService> { ClassService(get()) }

    single<IFirebaseMessagingService> { FirebaseMessagingService() }
    single { BirthdayReminderScheduler(get(), get(), get()) }
}