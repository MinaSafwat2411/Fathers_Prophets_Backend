package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.attendance.AddAttendanceRequest
import com.fathersprophets.backend.models.attendance.UpdateAttendanceRequest
import com.fathersprophets.backend.plugins.forbidRoles
import com.fathersprophets.backend.services.attendance.IAttendanceService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.attendanceRoutes(attendanceService: IAttendanceService) {
    route("/attendance") {
        post {
            call.forbidRoles("member")
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<AddAttendanceRequest>()
            val response = attendanceService.addAttendance(request, lang)
            call.respond(if (response.success) HttpStatusCode.Created else HttpStatusCode.BadRequest, response)
        }

        get {
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.getAllAttendance(lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        get("/session/{sessionId}") {
            val sessionId = call.parameters["sessionId"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.getAttendanceBySessionId(sessionId, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        get("/member/{memberId}") {
            val memberId = call.parameters["memberId"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.getAttendanceByUserId(memberId, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        get("/class/{classId}") {
            val classId = call.parameters["classId"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.getAttendanceByClassId(classId, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        put("/{id}") {
            call.forbidRoles("member")
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val request = call.receive<UpdateAttendanceRequest>()
            val response = attendanceService.updateAttendance(id, request, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }

        delete("/{id}") {
            call.forbidRoles("member")
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val response = attendanceService.deleteAttendance(id, lang)
            call.respond(if (response.success) HttpStatusCode.OK else HttpStatusCode.BadRequest, response)
        }
    }
}
