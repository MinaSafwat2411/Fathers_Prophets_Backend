package com.fathersprophets.backend.models.users

import com.fathersprophets.backend.modules.users.UserRole
import com.fathersprophets.backend.models.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequest(
    val oldPassword: String? = null,
    val newPassword: String? = null
) {
    fun toUserDto(id: Int, hashPassword: String) = UserDto(
        id = id,
        name = "",
        username = "",
        passwordHash = hashPassword,
        role = UserRole.member,
    )
}
