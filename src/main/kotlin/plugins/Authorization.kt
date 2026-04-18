package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.exceptions.ForbiddenException
import com.fathersprophets.backend.exceptions.userReviewed
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.exceptions.userRole
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal

fun ApplicationCall.requireRole(vararg roles: String) {

    val role = this.userRole()

    if (role == null || role !in roles) {
        val lang = request.headers["Accept-Language"] ?: "en"
        throw ForbiddenException(Localization.get("access_denied", lang))
    }
}

fun ApplicationCall.forbidRoles(vararg forbidden: String) {
    val role = userRole()
    val lang = request.headers["Accept-Language"] ?: "en"

    if (role != null && role in forbidden) {
        throw ForbiddenException(Localization.get("access_denied", lang))
    }
}

fun ApplicationCall.requireReviewed(){
    val isReviewed = this.userReviewed()

    if (isReviewed == null || !isReviewed) {
        val lang = request.headers["Accept-Language"] ?: "en"
        throw ForbiddenException(Localization.get("access_denied", lang))
    }
}
