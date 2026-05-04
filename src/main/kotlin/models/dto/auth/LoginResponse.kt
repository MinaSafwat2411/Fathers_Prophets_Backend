package com.fathersprophets.backend.models.response.auth

import com.fathersprophets.backend.models.dto.users.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val user: UserResponse,
    val token: String,
    val refreshToken: String
)