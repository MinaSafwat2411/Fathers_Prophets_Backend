package com.fathersprophets.backend.models.users

import com.fathersprophets.backend.database.tables.users.UserRole
import com.fathersprophets.backend.models.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val profile: String? = null
) {
    fun toUserDto(id: Int) = UserDto(
        id = id,
        name = "",
        username = "",
        passwordHash = "",
        role = UserRole.member,
        profile = profile
    )
}