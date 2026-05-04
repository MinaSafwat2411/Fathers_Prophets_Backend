package com.fathersprophets.backend.models.request.users

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
