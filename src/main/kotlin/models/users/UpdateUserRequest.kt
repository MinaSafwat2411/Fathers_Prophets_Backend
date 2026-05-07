package com.fathersprophets.backend.models.users

import com.fathersprophets.backend.models.dto.ParentsDto
import com.fathersprophets.backend.models.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val address: String? = null,
    val birthDate: String? = null,
    val fatherName: String? = null,
    val isShams: Boolean? = null,
    val memberId: String? = null,
    val skipMembership: Boolean? = null,
    val motherPhone : String? = null,
    val fatherPhone : String? = null,
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
        skipMembership = skipMembership,
        parents = ParentsDto(
            motherPhone = motherPhone,
            fatherPhone = fatherPhone
        )
    )
}
