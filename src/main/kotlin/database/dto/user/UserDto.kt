package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.enums.UserRole
import java.time.LocalDate

data class UserDto(
    val id: Int,
    val name: String,
    val username: String,
    val password : String,
    val email: String?,
    val phone: String?,
    val address: String?,
    val birthDate: LocalDate?,
    val fatherConfession: String?,
    val fatherPhone: String?,
    val motherPhone: String?,
    val isShams: Boolean,
    val profile: String?,
    val isReviewed: Boolean,
    val role: UserRole,
    val memberId: String?,
    val familyId: Int?,
    val classId: Int?,
    val score: Int
)