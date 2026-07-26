package com.fathersprophets.backend.routes.setting

import com.fathersprophets.backend.plugins.RateLimitPlugin
import com.fathersprophets.backend.services.version.IVersionService
import io.ktor.server.request.header
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.settingRoutes(versionService: IVersionService) {
    route("/setting") {
//        install(RateLimitPlugin)

        get {
            val lang = call.request.header("Accept-Language") ?: "en"
            val result = versionService.getLastVersion(lang)
            call.respond(result)
        }
    }
}
