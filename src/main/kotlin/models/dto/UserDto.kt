package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.UserRole
import com.fathersprophets.backend.models.users.UserResponse

data class UserDto(
    val id: Int,
    val name: String,
    val username: String,
    val passwordHash: String,
    val role: UserRole,
    val isReviewed: Boolean? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val birthDate: String? = null,
    val fatherName: String? = null,
    val isShams: Boolean? = null,
    val profile: String? = null,
    val memberId: String? = null,
    val token: String? = null,
    val refreshToken: String? = null,
    val fcmToken: String? = null,
    val skipMembership: Boolean? = null,
    val comments: List<String> = emptyList(),
    val parents: ParentsDto? = null,
){
    fun convertToUserResponse() = UserResponse(
        id = this.id,
        name = this.name,
        username = this.username,
        role = this.role.name,
        email = this.email,
        phone = this.phone,
        address = this.address,
        birthDate = this.birthDate,
        fatherName = this.fatherName,
        isShams = this.isShams,
        profile = this.profile,
        isReviewed = this.isReviewed,
        memberId = this.memberId,
        parents = this.parents?.convertToParentsResponse()
    )
}