package com.fathersprophets.backend.plugins

import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}