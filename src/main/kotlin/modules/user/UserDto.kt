package com.fathersprophets.backend.modules.user

import com.fathersprophets.backend.database.enums.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val fullName: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String?,
    val phone: String?,
    val address: String?,
    val birthDate: String?,
    val fatherConfession: String?,
    val fatherPhone: String?,
    val motherPhone: String?,
    val isShams: Boolean,
    val profile: String?,
    val isReviewed: Boolean,
    val isVerified: Boolean,
    val role: UserRole,
    val memberId: String?,
    val familyId: Int?,
    val classId: Int?,
    val score: Int
)

@Serializable
data class UserCreateDto(
    val firstName: String,
    val lastName: String,
    val password: String,
    val username: String,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val birthDate: String? = null,
    val fatherConfession: String? = null,
    val fatherPhone: String? = null,
    val motherPhone: String? = null,
    val isShams: Boolean = false,
    val profile: String? = null,
    val isReviewed: Boolean = false,
    val isVerified: Boolean = false,
    val role: UserRole,
    val memberId: String? = null,
    val familyId: Int? = null,
    val classId: Int? = null,
    val score: Int = 0
)

@Serializable
data class UserUpdateDto(
    val firstName: String? = null,
    val lastName: String? = null,
    val password: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val birthDate: String? = null,
    val fatherConfession: String? = null,
    val fatherPhone: String? = null,
    val motherPhone: String? = null,
    val isShams: Boolean? = null,
    val profile: String? = null,
    val isReviewed: Boolean? = null,
    val isVerified: Boolean? = null,
    val role: UserRole? = null,
    val memberId: String? = null,
    val familyId: Int? = null,
    val classId: Int? = null,
    val score: Int? = null
)