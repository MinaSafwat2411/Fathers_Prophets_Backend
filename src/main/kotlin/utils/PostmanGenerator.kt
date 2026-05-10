package com.fathersprophets.backend.utils

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File

// ==================== Data Classes ====================

@Serializable
data class PostmanCollection(
    val info: CollectionInfo,
    val item: List<CollectionFolder>,
    val variable: List<Variable>
)

@Serializable
data class CollectionInfo(
    val _postman_id: String,
    val name: String,
    val description: String,
    val schema: String
)

@Serializable
data class CollectionFolder(
    val name: String,
    val item: List<RequestItem>
)

@Serializable
data class RequestItem(
    val name: String,
    val request: Request,
    val response: List<String> = emptyList()
)

@Serializable
data class Request(
    val method: String,
    val header: List<Header>,
    val body: RequestBody? = null,
    val url: Url
)

@Serializable
data class Header(
    val key: String,
    val value: String
)

@Serializable
data class RequestBody(
    val mode: String,
    val raw: String
)

@Serializable
data class Url(
    val raw: String,
    val host: List<String>,
    val path: List<String>
)

@Serializable
data class Variable(
    val key: String,
    val value: String
)

@Serializable
data class PostmanEnvironment(
    val id: String,
    val name: String,
    val values: List<EnvironmentValue>
)

@Serializable
data class EnvironmentValue(
    val key: String,
    val value: String,
    val enabled: Boolean = true
)

// ==================== Generator ====================

object PostmanGenerator {
    private val json = Json { prettyPrint = true }

    fun generateCollection(outputPath: String = "Fathers_Prophets_API.postman_collection.json") {
        val collection = PostmanCollection(
            info = CollectionInfo(
                _postman_id = "fathers-prophets-api",
                name = "Fathers & Prophets Backend API",
                description = "Complete API collection for Fathers & Prophets Backend",
                schema = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
            ),
            item = listOf(
                createAuthFolder(),
                createUsersFolder(),
                createProfileFolder(),
                createClassesFolder(),
                createClassMembersFolder(),
                createSettingsFolder(),
                createHealthCheckFolder(),
                createCommentsFolder(),
                createSessionFolder(),
                createAttendanceFolder()
            ),
            variable = listOf(
                Variable("base_url", "http://localhost:8080"),
                Variable("access_token", "")
            )
        )

        val jsonString = json.encodeToString(collection)
        File(outputPath).writeText(jsonString)
        println("✓ Postman Collection generated: $outputPath")
    }

    fun generateEnvironment(outputPath: String = "Fathers_Prophets_API.postman_environment.json") {
        val environment = PostmanEnvironment(
            id = "fathers-prophets-env",
            name = "Fathers & Prophets Environment",
            values = listOf(
                EnvironmentValue("base_url", "http://localhost:8080", true),
                EnvironmentValue("access_token", "", true),
                EnvironmentValue("refresh_token", "", true),
                EnvironmentValue("admin_token", "", true),
                EnvironmentValue("user_id", "1", true),
                EnvironmentValue("class_id", "1", true),
                EnvironmentValue("member_id", "1", true),
                EnvironmentValue("session_id", "1", true),
                EnvironmentValue("attendance_id", "1", true)
            )
        )

        val jsonString = json.encodeToString(environment)
        File(outputPath).writeText(jsonString)
        println("✓ Postman Environment generated: $outputPath")
    }

    private fun createAuthFolder(): CollectionFolder {
        return CollectionFolder(
            name = "Authentication",
            item = listOf(
                createRequest(
                    name = "Login",
                    method = "POST",
                    path = "auth/login",
                    body = """{"username": "testuser", "password": "password123", "fcmToken": "fcm_token_here"}"""
                ),
                createRequest(
                    name = "Register",
                    method = "POST",
                    path = "auth/register",
                    body = """{"name": "John Doe", "username": "johndoe", "password": "password123", "phone": "1234567890", "address": "123 Main St", "birthDate": "1990-01-01", "fatherName": "Father Name", "isShams": false, "memberId": "member123"}"""
                ),
                createRequest(
                    name = "Refresh Token",
                    method = "POST",
                    path = "auth/refresh-token",
                    body = """{"refreshToken": "refresh_token_here"}"""
                ),
                createRequest(
                    name = "Logout",
                    method = "POST",
                    path = "auth/logout",
                    headers = listOf(
                        Header("Authorization", "Bearer {{access_token}}"),
                        Header("Accept-Language", "en")
                    )
                )
            )
        )
    }

    private fun createUsersFolder(): CollectionFolder {
        return CollectionFolder(
            name = "Users",
            item = listOf(
                createRequest("Get All Users", "GET", "users"),
                createRequest("Get Unreviewed Users", "GET", "users/unreviewed"),
                createRequest("Get Users by Role", "GET", "users/role/admin"),
                createRequest("Get User by ID", "GET", "users/1"),
                createRequest(
                    name = "Add User",
                    method = "POST",
                    path = "users",
                    body = """{"name": "New User", "username": "newuser", "password": "password123", "role": "member", "isReviewed": true, "phone": "1234567890"}"""
                ),
                createRequest("Update User Review", "PUT", "users/1/review"),
                createRequest(
                    name = "Update User",
                    method = "PUT",
                    path = "users/1",
                    body = """{"id": 1, "fieldName": "username", "newValue": "updatedusername"}"""
                ),
                createRequest("Delete User", "DELETE", "users/1")
            )
        )
    }

    private fun createProfileFolder(): CollectionFolder {
        return CollectionFolder(
            name = "Profile",
            item = listOf(
                createRequest("Get Profile", "GET", "profile"),
                createRequest(
                    name = "Update Email",
                    method = "PUT",
                    path = "profile/email",
                    body = """{"newEmail": "newemail@example.com"}"""
                ),
                createRequest(
                    name = "Update Password",
                    method = "PUT",
                    path = "profile/password",
                    body = """{"oldPassword": "oldpassword123", "newPassword": "newpassword123"}"""
                ),
                createRequest(
                    name = "Update Phone",
                    method = "PUT",
                    path = "profile/phone",
                    body = """{"newPhone": "9876543210"}"""
                )
            )
        )
    }

    private fun createClassesFolder(): CollectionFolder {
        return CollectionFolder(
            name = "Classes",
            item = listOf(
                createRequest("Get All Classes", "GET", "classes"),
                createRequest("Get Class by ID", "GET", "classes/1"),
                createRequest(
                    name = "Create Class",
                    method = "POST",
                    path = "classes",
                    body = """{"name": "Islamic History", "image": "https://example.com/image.jpg"}"""
                ),
                createRequest(
                    name = "Update Class",
                    method = "PUT",
                    path = "classes/1",
                    body = """{"name": "Updated Islamic History", "image": "https://example.com/new-image.jpg"}"""
                ),
                createRequest("Delete Class", "DELETE", "classes/1")
            )
        )
    }

    private fun createClassMembersFolder(): CollectionFolder {
        return CollectionFolder(
            name = "Class Members",
            item = listOf(
                createRequest("Get Class Members", "GET", "class-members/1"),
                createRequest(
                    name = "Add Class Member",
                    method = "POST",
                    path = "class-members",
                    body = """{"userId": 1, "classId": 1, "joinDate": "2024-01-15"}"""
                ),
                createRequest(
                    name = "Update Class Member",
                    method = "PUT",
                    path = "class-members/1",
                    body = """{"userId": 1, "classId": 2, "joinDate": "2024-01-20"}"""
                ),
                createRequest("Delete Class Member", "DELETE", "class-members/1")
            )
        )
    }

    private fun createSettingsFolder(): CollectionFolder {
        return CollectionFolder(
            name = "Settings",
            item = listOf(
                createRequest("Get Last Version", "GET", "setting")
            )
        )
    }

    private fun createHealthCheckFolder(): CollectionFolder {
        return CollectionFolder(
            name = "Health Check",
            item = listOf(
                createRequest("Health Check", "GET", "healthcheck", headers = listOf(Header("Accept-Language", "en")))
            )
        )
    }

    private fun createCommentsFolder(): CollectionFolder {
        return CollectionFolder(
            name = "Comments",
            item = listOf(
                createRequest(
                    name = "Add Comment",
                    method = "POST",
                    path = "comments/add",
                    body = """{"userId": 1, "content": "This is a comment", "classId": 1}"""
                ),
                createRequest(
                    name = "Update Comment",
                    method = "PUT",
                    path = "comments/update",
                    body = """{"id": 1, "userId": 1, "content": "Updated comment content", "classId": 1}"""
                ),
                createRequest("Delete Comment", "DELETE", "comments/delete/1"),
                createRequest("Get Comments by User ID", "GET", "comments/user/1"),
                createRequest("Get All Comments", "GET", "comments/all")
            )
        )
    }

    private fun createSessionFolder(): CollectionFolder{
        return CollectionFolder(
            name = "Sessions",
            item = listOf(
                createRequest(
                    name = "Get All Sessions",
                    method = "GET",
                    path = "sessions"
                ),
                createRequest(
                    name = "Get Session By ID",
                    method = "GET",
                    path = "sessions/{{session_id}}"
                ),
                createRequest(
                    name = "Create Session",
                    method = "POST",
                    path = "sessions",
                    body = """{"dateTime": "2024-10-15T18:00:00"}"""
                ),
                createRequest(
                    name = "Update Session",
                    method = "PUT",
                    path = "sessions/{{session_id}}",
                    body = """{"dateTime": "2024-10-15T19:00:00"}"""
                ),
                createRequest(
                    name = "Delete Session",
                    method = "DELETE",
                    path = "sessions/{{session_id}}"
                )
            )
        )
    }

    private fun createAttendanceFolder(): CollectionFolder {
        return CollectionFolder(
            name = "Attendance",
            item = listOf(
                createRequest(
                    name = "Add Attendance",
                    method = "POST",
                    path = "attendance",
                    body = """{"userId": 1, "sessionId": 1, "name": "John Doe", "attended": true, "broughtBible": true, "shmas": false, "odas": true, "tnawl": true, "classId": 1}"""
                ),
                createRequest(
                    name = "Get All Attendance",
                    method = "GET",
                    path = "attendance"
                ),
                createRequest(
                    name = "Get Attendance By Session ID",
                    method = "GET",
                    path = "attendance/session/{{session_id}}"
                ),
                createRequest(
                    name = "Get Attendance By Member ID",
                    method = "GET",
                    path = "attendance/member/{{member_id}}"
                ),
                createRequest(
                    name = "Get Attendance By Class ID",
                    method = "GET",
                    path = "attendance/class/{{class_id}}"
                ),
                createRequest(
                    name = "Update Attendance",
                    method = "PUT",
                    path = "attendance/{{attendance_id}}",
                    body = """{"attended": false, "broughtBible": false, "shmas": false, "odas": false, "tnawl": false, "classId": 1}"""
                ),
                createRequest(
                    name = "Delete Attendance",
                    method = "DELETE",
                    path = "attendance/{{attendance_id}}"
                )
            )
        )
    }


    private fun createRequest(
        name: String,
        method: String,
        path: String,
        body: String? = null,
        headers: List<Header> = getDefaultHeaders(method, body != null)
    ): RequestItem {
        val url = "{{base_url}}/api/v1/$path"
        val pathList = path.split("/")

        return RequestItem(
            name = name,
            request = Request(
                method = method,
                header = headers,
                body = body?.let { RequestBody("raw", it) },
                url = Url(
                    raw = url,
                    host = listOf("{{base_url}}"),
                    path = listOf("api", "v1") + pathList
                )
            )
        )
    }

    private fun getDefaultHeaders(method: String, hasBody: Boolean): List<Header> {
        val headers = mutableListOf(
            Header("Accept-Language", "en")
        )

        if (method != "GET" && method != "DELETE" || hasBody) {
            headers.add(0, Header("Content-Type", "application/json"))
        }

        headers.add(Header("Authorization", "Bearer {{access_token}}"))

        return headers
    }
}

// ==================== Main Function ====================

fun main() {
    println("🚀 Postman Collection & Environment Generator")
    println("=" * 50)

    PostmanGenerator.generateCollection()
    PostmanGenerator.generateEnvironment()

    println("=" * 50)
    println("✓ Generation complete!")
    println("\nFiles created:")
    println("  1. Fathers_Prophets_API.postman_collection.json")
    println("  2. Fathers_Prophets_API.postman_environment.json")
    println("\nImport them into Postman:")
    println("  File → Import → Choose both JSON files")
}

operator fun String.times(count: Int): String = this.repeat(count)
