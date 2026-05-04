package com.fathersprophets.backend.models.request.users

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val id: Int,
    val address: String? = null,
    val birthDate: String? = null,
    val fatherName: String? = null,
    val isShams: Boolean? = null,
    val memberId: String? = null,
    val skipMembership: Boolean? = null
)
