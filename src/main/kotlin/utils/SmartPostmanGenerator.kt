package com.fathersprophets.backend.utils

import com.fathersprophets.backend.models.auth.LoginRequest
import com.fathersprophets.backend.models.auth.RefreshRequest
import com.fathersprophets.backend.models.auth.RegisterRequest
import com.fathersprophets.backend.models.classes.CreateClassRequest
import com.fathersprophets.backend.models.classes.UpdateClassRequest
import com.fathersprophets.backend.models.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.UpdateCommentRequest
import com.fathersprophets.backend.models.event.EventRequest
import com.fathersprophets.backend.models.eventmember.EventMemberRequest
import com.fathersprophets.backend.models.session.AddSessionRequest
import com.fathersprophets.backend.models.session.UpdateSessionRequest
import com.fathersprophets.backend.models.attendance.AddAttendanceRequest
import com.fathersprophets.backend.models.attendance.UpdateAttendanceRequest
import com.fathersprophets.backend.models.person.UpdatePersonRequest
import com.fathersprophets.backend.models.personquestion.CreateQuestionRequest
import com.fathersprophets.backend.models.personquestion.UpdateQuestionRequest
import com.fathersprophets.backend.models.personmcq.CreatePersonMcqRequest
import com.fathersprophets.backend.models.personmcq.UpdatePersonMcqRequest
import com.fathersprophets.backend.models.personanswer.CreatePersonAnswerRequest
import com.fathersprophets.backend.models.personanswer.UpdatePersonAnswerRequest
import com.fathersprophets.backend.models.personanswer.UpdateAnswerStatusRequest
import com.fathersprophets.backend.models.users.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

// ==================== Model-Aware Request Builders ====================

/**
 * Generates realistic example values based on field names and types
 */

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
data class PostmanScript(
    val type: String = "text/javascript",
    val exec: List<String>
)

@Serializable
data class PostmanEvent(
    val listen: String,
    val script: PostmanScript
)

@Serializable
data class RequestItem(
    val name: String,
    val request: Request,
    val event: List<PostmanEvent> = emptyList(),
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

object ExampleValueGenerator {
    fun generateValue(fieldName: String, fieldType: KClass<*>, isNullable: Boolean = true): Any? {
        return when {
            isNullable && fieldName.contains("token", ignoreCase = true) -> null
            isNullable && fieldName.contains("id") && fieldType == Int::class -> 1
            isNullable && fieldName.contains("id") && fieldType == String::class -> "id_123"

            fieldName.contains("username", ignoreCase = true) -> "testuser"
            fieldName.contains("password", ignoreCase = true) -> "password123"
            fieldName.contains("email", ignoreCase = true) -> "user@example.com"
            fieldName.contains("phone", ignoreCase = true) -> "1234567890"
            fieldName.contains("name", ignoreCase = true) -> "John Doe"
            fieldName.contains("address", ignoreCase = true) -> "123 Main St"
            fieldName.contains("birthdate", ignoreCase = true) -> "1990-01-01"
            fieldName.contains("fathername", ignoreCase = true) -> "Father Name"
            fieldName.contains("image", ignoreCase = true) -> "https://example.com/image.jpg"
            fieldName.contains("role", ignoreCase = true) -> "member"
            fieldName.contains("token", ignoreCase = true) -> "fcm_token_here"
            fieldName.contains("pin", ignoreCase = true) -> "1234"
            fieldName.contains("version", ignoreCase = true) -> "1.0.0"
            fieldName.contains("joindate", ignoreCase = true) -> "2024-01-15"
            fieldName.contains("datetime", ignoreCase = true) -> "2024-10-15T18:00:00"

            fieldType == Boolean::class -> false
            fieldType == Int::class -> 1
            fieldType == String::class -> "value"

            else -> null
        }
    }

    fun generateJsonFromModel(modelClass: KClass<*>): String? {
        return try {
            val properties = modelClass.declaredMemberProperties
            val jsonContent = properties.joinToString(",\n    ") { prop ->
                val returnType = prop.returnType
                val isNullable = returnType.isMarkedNullable
                val typeClass = returnType.jvmErasure

                val value = generateValue(prop.name, typeClass, isNullable)

                val jsonValue = when (value) {
                    is String -> "\"${value.replace("\"", "\\\"")}\"" // Escape quotes
                    null -> "null"
                    else -> value.toString()
                }
                "\"${prop.name}\": $jsonValue"
            }

            "{\n    $jsonContent\n}"
        } catch (e: Exception) {
            println("⚠️ Error generating body for ${modelClass.simpleName}: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}

// ==================== Enhanced Request Builder ====================

object PostmanScripts {
    val statusCheck = listOf(
        "pm.test(\"Status is 200\", function () {",
        "    pm.response.to.have.status(200);",
        "});"
    )

    val captureTokens = listOf(
        "var jsonData = pm.response.json();",
        "pm.test(\"Status is 200\", function () {",
        "    pm.response.to.have.status(200);",
        "});",
        "if (jsonData.success && jsonData.data) {",
        "    pm.environment.set(\"access_token\", jsonData.data.token);",
        "    pm.environment.set(\"refresh_token\", jsonData.data.refreshToken);",
        "    console.log(\"Tokens captured successfully\");",
        "}"
    )

    val captureRefreshedTokens = listOf(
        "var jsonData = pm.response.json();",
        "pm.test(\"Status is 200\", function () {",
        "    pm.response.to.have.status(200);",
        "});",
        "if (jsonData.success && jsonData.data) {",
        "    pm.environment.set(\"access_token\", jsonData.data.token);",
        "    pm.environment.set(\"refresh_token\", jsonData.data.refreshToken);",
        "    console.log(\"Tokens refreshed successfully\");",
        "}"
    )
}

data class RequestDefinition(
    val name: String,
    val method: String,
    val path: String,
    val requestBodyModel: KClass<*>? = null,
    val requiresAuth: Boolean = true,
    val testScript: List<String>? = null
)

object EnhancedPostmanGenerator {
    private val json = Json { prettyPrint = true }

    fun generateCollectionWithModels(
        requests: List<RequestDefinition>,
        outputPath: String = "Fathers_Prophets_API_Smart.postman_collection.json"
    ) {
        val folders = mutableMapOf<String, MutableList<RequestItem>>()

        // Group requests by folder (first part of path after /api/v1)
        for (request in requests) {
            val folderName = request.path.split("/").firstOrNull()?.replaceFirstChar { it.uppercase() } ?: "Other"
            folders.getOrPut(folderName) { mutableListOf() }
                .add(createSmartRequest(request))
        }

        val collection = PostmanCollection(
            info = CollectionInfo(
                _postman_id = "fathers-prophets-api-smart",
                name = "Fathers & Prophets Backend API (Model-Based)",
                description = "Auto-generated collection with models from request classes",
                schema = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
            ),
            item = folders.map { (name, items) ->
                CollectionFolder(name = name, item = items)
            },
            variable = listOf(
                Variable("base_url", "http://localhost:8080"),
                Variable("access_token", ""),
                Variable("refresh_token", ""),
                Variable("admin_token", ""),
                Variable("user_id", "1"),
                Variable("class_id", "1"),
                Variable("member_id", "1"),
                Variable("session_id", "1"),
                Variable("attendance_id", "1")
            )
        )

        val jsonString = json.encodeToString(collection)
        File(outputPath).writeText(jsonString)
        println("✓ Smart Postman Collection generated: $outputPath")
    }

    fun generateEnvironment(outputPath: String = "Fathers_Prophets_API_Smart.postman_environment.json") {
        val environment = PostmanEnvironment(
            id = "fathers-prophets-api-smart-env",
            name = "Fathers & Prophets Local",
            values = listOf(
                EnvironmentValue("base_url", "http://localhost:8080"),
                EnvironmentValue("access_token", "YOUR_TOKEN_HERE"),
                EnvironmentValue("refresh_token", ""),
                EnvironmentValue("admin_token", ""),
                EnvironmentValue("user_id", "1"),
                EnvironmentValue("class_id", "1"),
                EnvironmentValue("member_id", "1"),
                EnvironmentValue("session_id", "1"),
                EnvironmentValue("attendance_id", "1")
            )
        )

        val jsonString = json.encodeToString(environment)
        File(outputPath).writeText(jsonString)
        println("✓ Smart Postman Environment generated: $outputPath")
    }

    private fun createSmartRequest(request: RequestDefinition): RequestItem {
        val headers = mutableListOf(
            Header("Accept-Language", "en")
        )

        var body: RequestBody? = null

        // Add Content-Type and generate body if needed
        if (request.method in listOf("POST", "PUT", "PATCH")) {
            headers.add(0, Header("Content-Type", "application/json"))

            // Generate body from model
            if (request.requestBodyModel != null) {
                val generatedBody = ExampleValueGenerator.generateJsonFromModel(request.requestBodyModel)
                if (generatedBody != null) {
                    body = RequestBody("raw", generatedBody)
                }
            }
        }

        // Add auth header for protected routes
        if (request.requiresAuth) {
            headers.add(Header("Authorization", "Bearer {{access_token}}"))
        }

        val url = "{{base_url}}/api/v1/${request.path}"
        val pathList = request.path.split("/")

        val scriptLines = request.testScript ?: PostmanScripts.statusCheck
        val events = listOf(
            PostmanEvent(
                listen = "test",
                script = PostmanScript(exec = scriptLines)
            )
        )

        return RequestItem(
            name = request.name,
            request = Request(
                method = request.method,
                header = headers,
                body = body,
                url = Url(
                    raw = url,
                    host = listOf("{{base_url}}"),
                    path = listOf("api", "v1") + pathList
                )
            ),
            event = events
        )
    }
}

// ==================== Predefined Endpoints with Models ====================

object PostmanEndpoints {
    val allEndpoints = listOf(
        // Auth
        RequestDefinition("Login", "POST", "auth/login", LoginRequest::class, requiresAuth = false, testScript = PostmanScripts.captureTokens),
        RequestDefinition("Register", "POST", "auth/register", RegisterRequest::class, requiresAuth = false, testScript = PostmanScripts.captureTokens),
        RequestDefinition("Refresh Token", "POST", "auth/refresh-token", RefreshRequest::class, requiresAuth = false, testScript = PostmanScripts.captureRefreshedTokens),
        RequestDefinition("Logout", "POST", "auth/logout"),

        // Users
        RequestDefinition("Get All Users", "GET", "users"),
        RequestDefinition("Get Unreviewed Users", "GET", "users/unreviewed"),
        RequestDefinition("Get Users by Role", "GET", "users/role/admin"),
        RequestDefinition("Get User by ID", "GET", "users/1"),
        RequestDefinition("Add User", "POST", "users", AddUserRequest::class),
        RequestDefinition("Update User Review", "PUT", "users/1/review"),
        RequestDefinition("Update User", "PUT", "users/1", UpdateUserRequest::class),
        RequestDefinition("Delete User", "DELETE", "users/1"),

        // Profile
        RequestDefinition("Get Profile", "GET", "profile"),
        RequestDefinition("Update Email", "PUT", "profile/email", UpdateEmailRequest::class),
        RequestDefinition("Update Password", "PUT", "profile/password", UpdatePasswordRequest::class),
        RequestDefinition("Update Phone", "PUT", "profile/phone", UpdatePhoneRequest::class),
        RequestDefinition("Update Profile", "PUT", "profile", UpdateProfileRequest::class),

        // Classes
        RequestDefinition("Get All Classes", "GET", "classes"),
        RequestDefinition("Get Class by ID", "GET", "classes/1"),
        RequestDefinition("Create Class", "POST", "classes", CreateClassRequest::class),
        RequestDefinition("Update Class", "PUT", "classes/1", UpdateClassRequest::class),
        RequestDefinition("Delete Class", "DELETE", "classes/1"),

        // Class Members
        RequestDefinition("Get Class Members", "GET", "class-members/1"),
        RequestDefinition("Add Class Member", "POST", "class-members", AddClassMemberRequest::class),
        RequestDefinition("Update Class Member", "PUT", "class-members/1", UpdateClassMemberRequest::class),
        RequestDefinition("Delete Class Member", "DELETE", "class-members/1"),

        // Comments
        RequestDefinition("Add Comment", "POST", "comments/add", AddCommentRequest::class),
        RequestDefinition("Update Comment", "PUT", "comments/update", UpdateCommentRequest::class),
        RequestDefinition("Delete Comment", "DELETE", "comments/delete/1"),
        RequestDefinition("Get Comments by User ID", "GET", "comments/user/1"),
        RequestDefinition("Get All Comments", "GET", "comments/all"),

        // Settings
        RequestDefinition("Get Last Version", "GET", "setting", requiresAuth = false),

        // Health
        RequestDefinition("Health Check", "GET", "healthcheck", requiresAuth = false),

        // Events
        RequestDefinition("Get All Events", "GET", "events"),
        RequestDefinition("Get Event by ID", "GET", "events/1"),
        RequestDefinition("Create Event", "POST", "events", EventRequest::class),
        RequestDefinition("Update Event", "PUT", "events/1", EventRequest::class),
        RequestDefinition("Delete Event", "DELETE", "events/1"),

        // Event Members
        RequestDefinition("Get Event Members", "GET", "event-members/1"),
        RequestDefinition("Add Event Member", "POST", "event-members", EventMemberRequest::class),
        RequestDefinition("Delete Event Member", "DELETE", "event-members/1"),
        
        // Sessions
        RequestDefinition("Get All Sessions", "GET", "sessions"),
        RequestDefinition("Get Session By ID", "GET", "sessions/{{session_id}}"),
        RequestDefinition("Create Session", "POST", "sessions", AddSessionRequest::class),
        RequestDefinition("Update Session", "PUT", "sessions/{{session_id}}", UpdateSessionRequest::class),
        RequestDefinition("Delete Session", "DELETE", "sessions/{{session_id}}"),
        
        // Person
        RequestDefinition("Get All Persons", "GET", "person"),
        RequestDefinition("Get Person by ID", "GET", "person/1"),
        RequestDefinition("Add Person", "POST", "person", UpdatePersonRequest::class),
        RequestDefinition("Update Person", "PUT", "person/1", UpdatePersonRequest::class),
        RequestDefinition("Delete Person", "DELETE", "person/1"),

        // Person Question
        RequestDefinition("Get All Person Questions", "GET", "person-question"),
        RequestDefinition("Get Person Question by ID", "GET", "person-question/1"),
        RequestDefinition("Get Person Questions by Person ID", "GET", "person-question/person/1"),
        RequestDefinition("Create Person Question", "POST", "person-question", CreateQuestionRequest::class),
        RequestDefinition("Update Person Question", "PUT", "person-question/1", UpdateQuestionRequest::class),
        RequestDefinition("Delete Person Question", "DELETE", "person-question/1"),

        // Person MCQ
        RequestDefinition("Get All Person MCQs", "GET", "person-mcq"),
        RequestDefinition("Get Person MCQ by ID", "GET", "person-mcq/1"),
        RequestDefinition("Get Person MCQs by Question ID", "GET", "person-mcq/question/1"),
        RequestDefinition("Create Person MCQ", "POST", "person-mcq", CreatePersonMcqRequest::class),
        RequestDefinition("Update Person MCQ", "PUT", "person-mcq/1", UpdatePersonMcqRequest::class),
        RequestDefinition("Delete Person MCQ", "DELETE", "person-mcq/1"),

        // Person Answer
        RequestDefinition("Get All Person Answers", "GET", "person-answer"),
        RequestDefinition("Get Person Answer by ID", "GET", "person-answer/1"),
        RequestDefinition("Get Person Answers by Question ID", "GET", "person-answer/question/1"),
        RequestDefinition("Get Person Answers by User ID", "GET", "person-answer/user/1"),
        RequestDefinition("Create Person Answer", "POST", "person-answer", CreatePersonAnswerRequest::class),
        RequestDefinition("Update Person Answer", "PUT", "person-answer/1", UpdatePersonAnswerRequest::class),
        RequestDefinition("Update Person Answer Status", "PATCH", "person-answer/1/status", UpdateAnswerStatusRequest::class),
        RequestDefinition("Delete Person Answer", "DELETE", "person-answer/1"),

        // Attendance
        RequestDefinition("Get All Attendance", "GET", "attendance"),
        RequestDefinition("Get Attendance By Session ID", "GET", "attendance/session/{{session_id}}"),
        RequestDefinition("Get Attendance By Member ID", "GET", "attendance/member/{{member_id}}"),
        RequestDefinition("Get Attendance By Class ID", "GET", "attendance/class/{{class_id}}"),
        RequestDefinition("Add Attendance", "POST", "attendance", AddAttendanceRequest::class),
        RequestDefinition("Update Attendance", "PUT", "attendance/{{attendance_id}}", UpdateAttendanceRequest::class),
        RequestDefinition("Delete Attendance", "DELETE", "attendance/{{attendance_id}}"),
    )
}

// ==================== Main Generation ====================

fun main() {
    println("🚀 Smart Postman Generator (Model-Based)")
    println("=" * 60)

    // New smart generator (model-based bodies)
    EnhancedPostmanGenerator.generateCollectionWithModels(
        PostmanEndpoints.allEndpoints,
        "Fathers_Prophets_API_ModelBased.postman_collection.json"
    )
    EnhancedPostmanGenerator.generateEnvironment(
        "Fathers_Prophets_API_ModelBased.postman_environment.json"
    )

    println("=" * 60)
    println("✅ Generation complete!")
    println("\nGenerated files:")
    println("  1. Fathers_Prophets_API_ModelBased.postman_collection.json (model-based)")
    println("  2. Fathers_Prophets_API_ModelBased.postman_environment.json")
    println("\nThe model-based collection will have:")
    println("  ✓ Request bodies auto-generated from models")
    println("  ✓ Realistic example values based on field names")
    println("  ✓ Proper typing and validation")
}

operator fun String.times(count: Int): String = this.repeat(count)

// ==================== Model Imports (Reference) ====================

// These would normally be imported from your models package
// For now, we use them by their KClass references in PostmanEndpoints

/**
 * Note: These are actual model classes from your codebase
 * The generator will use reflection on these classes to extract field information
 * and generate accurate example bodies
 */

// Auth Models - referenced via ::class
// LoginRequest, RegisterRequest, RefreshRequest - see models/auth/

// User Models - referenced via ::class
// AddUserRequest, UpdateUserRequest, UpdateEmailRequest, UpdatePasswordRequest, UpdatePhoneRequest - see models/users/

// Class Models - referenced via ::class
// CreateClassRequest, UpdateClassRequest - see models/classes/

// ClassMember Models - referenced via ::class
// AddClassMemberRequest, UpdateClassMemberRequest - see models/classmember/