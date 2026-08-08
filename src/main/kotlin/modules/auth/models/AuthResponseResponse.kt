package com.fathersprophets.backend.modules.auth.models

import com.fathersprophets.backend.database.tables.user.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseResponse(
    val user: UserDto,
    val token: String,
    val refreshToken: String,
    val expiresAt: Long
)