package com.fathersprophets.backend.models.dto.users

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequest(
    val oldPassword: String? = null,
    val newPassword: String? = null
)
