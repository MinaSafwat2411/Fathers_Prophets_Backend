package com.fathersprophets.backend.models.users

import com.fathersprophets.backend.database.tables.users.UserRole
import com.fathersprophets.backend.models.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class AddUserRequest(
    val name: String,
    val username: String,
    val password: String,
    val role: String,
    val isReviewed: Boolean? = null,
    val phone: String? = null,
    val address: String? = null,
    val birthDate: String? = null,
    val fatherName: String? = null,
    val isShams: Boolean? = null,
    val memberId: String? = null,
    val profile: String? = null,
) {
    fun toUserDto(id: Int, hashPassword: String) = UserDto(
        id = id,
        name = this.name,
        username = this.username,
        passwordHash = hashPassword,
        role = try {
            UserRole.valueOf(role)
        } catch (e: Exception) {
            UserRole.member
        },
        isReviewed = true,
        phone = this.phone,
        address = this.address,
        birthDate = this.birthDate,
        fatherName = this.fatherName,
        isShams = this.isShams,
        memberId = this.memberId,
        profile = this.profile
    )
}
