package com.fathersprophets.backend.models.dto.users

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val id: Int? = null,
    val address: String? = null,
    val birthDate: String? = null,
    val fatherName: String? = null,
    val isShams: Boolean? = null,
    val memberId: String? = null,
    val skipMembership: Boolean? = null
)
