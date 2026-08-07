package com.fathersprophets.backend.modules.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequest(
    val username: String
)