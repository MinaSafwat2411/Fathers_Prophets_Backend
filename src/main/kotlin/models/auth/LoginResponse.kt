package com.fathersprophets.backend.models.auth

import com.fathersprophets.backend.models.users.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val user: UserResponse,
    val token: String,
    val refreshToken: String
)