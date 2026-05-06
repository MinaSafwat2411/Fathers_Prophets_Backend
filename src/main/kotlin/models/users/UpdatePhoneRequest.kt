package com.fathersprophets.backend.models.users

import com.fathersprophets.backend.models.dto.UserDto
import kotlinx.serialization.Serializable


@Serializable
data class UpdatePhoneRequest(
    val phone: String? = null
) {
    fun toUserDto(id: Int): UserDto = UserDto(
        id = id,
        name = "",
        username = "",
        passwordHash = "",
        role = "",
    )
}
