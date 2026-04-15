package com.fathersprophets.backend

import com.fathersprophets.backend.database.config.DatabaseFactory
import com.fathersprophets.backend.plugins.configureDI
import com.fathersprophets.backend.plugins.configureRouting
import com.fathersprophets.backend.plugins.configureSerialization
import com.fathersprophets.backend.utils.Localization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    Localization.load()
    DatabaseFactory.init()
    configureDI()
    configureRouting()
    configureSerialization()
}
