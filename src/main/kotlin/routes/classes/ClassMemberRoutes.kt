package com.fathersprophets.backend.routes.classes

import com.fathersprophets.backend.models.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.plugins.requireRole
import com.fathersprophets.backend.services.classes.classmember.IClassMemberService
import com.fathersprophets.backend.utils.receiveMultipartForm
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.classMemberRoutes(classMemberService: IClassMemberService) {
    route("/class-members") {
        get("/{classId}") {
            val classId = call.parameters["classId"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"

            val result = classMemberService.findMemberClass(classId, lang)
            call.respond(HttpStatusCode.OK, result)
        }

        post {
            call.requireRole("admin", "superadmin")
            val lang = call.request.header("Accept-Language") ?: "en"
            val form = call.receiveMultipartForm(lang)

            val request = AddClassMemberRequest(
                userId = form.fields["userId"]?.toIntOrNull(),
                classId = form.fields["classId"]?.toIntOrNull(),
                isTeacher = form.fields["isTeacher"]?.toBooleanStrictOrNull(),
                name = form.fields["name"],
                image = form.imageUrl
            )

            val result = classMemberService.addMember(request, lang)
            call.respond(HttpStatusCode.Created, result)
        }

        put("/{id}") {
            call.requireRole("admin", "superadmin")
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            val form = call.receiveMultipartForm(lang)

            val request = UpdateClassMemberRequest(
                userId = form.fields["userId"]?.toIntOrNull() ?: 0,
                classId = form.fields["classId"]?.toIntOrNull() ?: 0,
                isTeacher = form.fields["isTeacher"]?.toBooleanStrictOrNull() ?: false,
                name = form.fields["name"] ?: "",
                image = form.imageUrl
            )

            val result = classMemberService.updateMember(id, request, lang)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            call.requireRole("admin", "superadmin")
            val id = call.parameters["id"]?.toIntOrNull()
            val lang = call.request.header("Accept-Language") ?: "en"
            
            val result = classMemberService.deleteMember(id, lang)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}
