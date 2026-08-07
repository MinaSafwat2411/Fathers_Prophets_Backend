package com.fathersprophets.backend.modules.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val resetVerifyToken: String,
    val newPassword: String
)