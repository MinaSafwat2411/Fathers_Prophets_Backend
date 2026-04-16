package com.fathersprophets.backend.models.dto

import java.time.LocalDate

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val passwordHash: String,
    val role: String,
    val email: String?,
    val phone: String?,
    val address: String?,
    val birthDate: LocalDate?,
    val fatherName: String?,
    val isShams: Boolean?,
    val profile: String?,
    val isReviewed: Boolean?,
    val classId: Int?,
    val memberId: String?
)
