package com.fathersprophets.backend.exceptions

import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.auth.*

fun ApplicationCall.userRole(): String? {
    return principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("role")
        ?.asString()
}

fun ApplicationCall.userReviewed(): Boolean? {
    return principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("isReviewed")
        ?.asBoolean()
}
