package com.fathersprophets.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
    val errorCode: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)