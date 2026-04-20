package com.fathersprophets.backend.routes

import com.fathersprophets.backend.models.request.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.request.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.classmember.IClassMemberService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.classMemberRoutes(classMemberService: IClassMemberService) {
    route("/class-members") {
        get("/{classId}") {
            val classId = call.parameters["classId"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid Class ID")
            val lang = call.request.header("Accept-Language") ?: "en"
            
            val result = classMemberService.findMemberClass(classId, lang)
            call.respond(HttpStatusCode.OK, result)
        }

        post {
            call.requireRole("admin", "superadmin")
            val request = call.receive<AddClassMemberRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"

            val result = classMemberService.addMember(request, lang)
            call.respond(HttpStatusCode.Created, result)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin")
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            val request = call.receive<UpdateClassMemberRequest>()
            val lang = call.request.header("Accept-Language") ?: "en"
            
            val result = classMemberService.updateMember(id, request, lang)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin")
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            val lang = call.request.header("Accept-Language") ?: "en"
            
            val result = classMemberService.deleteMember(id, lang)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}
