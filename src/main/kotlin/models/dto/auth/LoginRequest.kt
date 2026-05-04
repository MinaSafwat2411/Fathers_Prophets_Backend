package com.fathersprophets.backend.models.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
    val fcmToken : String,
)