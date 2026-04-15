package com.fathersprophets.backend.di

import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.repository.auth.AuthRepository
import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.services.AuthService
import com.fathersprophets.backend.services.IAuthService
import org.koin.dsl.module

val appModule = module {
    single { UserDao() }

    single<IAuthRepository> {
        AuthRepository(
            userDao = get(),
            jwtSecret = "secret",
            jwtIssuer = "http://0.0.0.0:8080/",
            jwtAudience = "http://0.0.0.0:8080/"
        )
    }

    single<IAuthService> { AuthService(get()) }
}
