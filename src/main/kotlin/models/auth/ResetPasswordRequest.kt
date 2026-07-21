package com.fathersprophets.backend.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val verifyToken: String? = null,
    val newPassword: String? = null
)