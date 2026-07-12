package com.fathersprophets.backend.models.auth

import com.fathersprophets.backend.database.tables.users.UserRole
import com.fathersprophets.backend.models.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String? = null,
    val password: String? = null,
    val fcmToken: String? = null,
) {
    fun toUserDto(hashPassword: String) = UserDto(
        id = 0,
        name = this.username ?: "",
        username = this.username ?: "",
        passwordHash = hashPassword,
        role = UserRole.member,
        isReviewed = false,
        fcmToken = this.fcmToken
    )
}