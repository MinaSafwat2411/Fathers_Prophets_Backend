package com.fathersprophets.backend.models.users

import com.fathersprophets.backend.models.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val address: String? = null,
    val birthDate: String? = null,
    val fatherName: String? = null,
    val isShams: Boolean? = null,
    val memberId: String? = null,
    val skipMembership: Boolean? = null
) {
    fun toUserDto(id: Int) = UserDto(
        id = id,
        name = "",
        username = "",
        passwordHash = "",
        role = "",
        address = address,
        birthDate = birthDate,
        fatherName = fatherName,
        isShams = isShams,
        memberId = memberId,
        skipMembership = skipMembership
    )
}
