package com.fathersprophets.backend.models.auth

import com.fathersprophets.backend.models.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    val refreshToken: String? = null
) {
    fun toUserDto() = UserDto(
        id = 0,
        name = "",
        username = "",
        passwordHash = "",
        role = "member",
        refreshToken = this.refreshToken
    )

}