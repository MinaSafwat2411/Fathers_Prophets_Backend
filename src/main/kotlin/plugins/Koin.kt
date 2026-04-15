package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.di.appModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {

    install(Koin) {
        modules(appModule)
    }
}