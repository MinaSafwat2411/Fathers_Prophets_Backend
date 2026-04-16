package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.exceptions.ForbiddenException
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.exceptions.userRole
import io.ktor.server.application.*

fun ApplicationCall.requireRole(vararg roles: String) {

    val role = this.userRole()

    if (role == null || role !in roles) {
        val lang = request.headers["Accept-Language"] ?: "en"
        throw ForbiddenException(Localization.get("access_denied", lang))
    }
}
