package com.fathersprophets.backend.base

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
    val errors: List<String>? = null,
    val statusCode: Int? = null,
    val timestamp: Long = System.currentTimeMillis(),
)
