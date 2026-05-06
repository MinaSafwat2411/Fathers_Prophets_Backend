package com.fathersprophets.backend.models.auth

import com.fathersprophets.backend.models.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String? = null,
    val username: String? = null,
    val password: String? = null,
    val fcmToken: String? = null
) {
    fun toUserDto(passwordHash: String) = UserDto(
        id = 0,
        name = this.name ?: "",
        username = this.username ?: "",
        passwordHash = passwordHash,
        role = "member",
        isReviewed = false,
        fcmToken = this.fcmToken
    )
}