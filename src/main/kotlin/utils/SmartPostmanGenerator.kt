package com.fathersprophets.backend.utils

import com.fathersprophets.backend.models.auth.LoginRequest
import com.fathersprophets.backend.models.auth.RegisterRequest
import com.fathersprophets.backend.models.auth.RefreshRequest
import com.fathersprophets.backend.models.users.AddUserRequest
import com.fathersprophets.backend.models.users.UpdateUserRequest
import com.fathersprophets.backend.models.users.UpdateEmailRequest
import com.fathersprophets.backend.models.users.UpdatePasswordRequest
import com.fathersprophets.backend.models.users.UpdatePhoneRequest
import com.fathersprophets.backend.models.users.UpdateProfileRequest
import com.fathersprophets.backend.models.classes.CreateClassRequest
import com.fathersprophets.backend.models.classes.UpdateClassRequest
import com.fathersprophets.backend.models.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.models.comments.AddCommentRequest
import com.fathersprophets.backend.models.comments.UpdateCommentRequest
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

// ==================== Model-Aware Request Builders ====================

/**
 * Generates realistic example values based on field names and types
 */
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

            fieldType == Boolean::class -> false
            fieldType == Int::class -> 1
            fieldType == String::class -> "value"

            else -> null
        }
    }

    fun generateJsonFromModel(modelClass: KClass<*>): String? {
        return try {
            val properties = modelClass.declaredMemberProperties
            val jsonMap = mutableMapOf<String, Any?>()

            for (prop in properties) {
                val returnType = prop.returnType
                val isNullable = returnType.isMarkedNullable
                val typeClass = returnType.jvmErasure

                val value = generateValue(prop.name, typeClass, isNullable)
                jsonMap[prop.name] = value
            }

            // Use kotlinx.serialization to convert to JSON
            val json = Json { prettyPrint = true }
            json.encodeToString(jsonMap)
        } catch (_: Exception) {
            null
        }
    }
}

// ==================== Enhanced Request Builder ====================

data class RequestDefinition(
    val name: String,
    val method: String,
    val path: String,
    val requestBodyModel: KClass<*>? = null,
    val requiresAuth: Boolean = true
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
                Variable("access_token", "")
            )
        )

        val jsonString = json.encodeToString(collection)
        File(outputPath).writeText(jsonString)
        println("✓ Smart Postman Collection generated: $outputPath")
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
            )
        )
    }
}

// ==================== Predefined Endpoints with Models ====================

object PostmanEndpoints {
    val allEndpoints = listOf(
        // Auth
        RequestDefinition("Login", "POST", "auth/login", LoginRequest::class, requiresAuth = false),
        RequestDefinition("Register", "POST", "auth/register", RegisterRequest::class, requiresAuth = false),
        RequestDefinition("Refresh Token", "POST", "auth/refresh-token", RefreshRequest::class, requiresAuth = false),
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
        RequestDefinition("Health Check", "GET", "healthcheck", requiresAuth = false)
    )
}

// ==================== Main Generation ====================

fun main() {
    println("🚀 Smart Postman Generator (Model-Based)")
    println("=" * 60)

    // Original generator (static bodies)
    PostmanGenerator.generateCollection()
    PostmanGenerator.generateEnvironment()

    // New smart generator (model-based bodies)
    EnhancedPostmanGenerator.generateCollectionWithModels(
        PostmanEndpoints.allEndpoints,
        "Fathers_Prophets_API_ModelBased.postman_collection.json"
    )

    println("=" * 60)
    println("✅ Generation complete!")
    println("\nGenerated files:")
    println("  1. Fathers_Prophets_API.postman_collection.json (original)")
    println("  2. Fathers_Prophets_API_ModelBased.postman_collection.json (model-based)")
    println("  3. Fathers_Prophets_API.postman_environment.json")
    println("\nThe model-based collection will have:")
    println("  ✓ Request bodies auto-generated from models")
    println("  ✓ Realistic example values based on field names")
    println("  ✓ Proper typing and validation")
}


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

