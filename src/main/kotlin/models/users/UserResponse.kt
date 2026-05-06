package com.fathersprophets.backend.models.users

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val username: String,
    val role: String,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val birthDate: String? = null,
    val fatherName: String? = null,
    val isShams: Boolean? = null,
    val profile: String? = null,
    val isReviewed: Boolean? = null,
    val memberId: String? = null
)