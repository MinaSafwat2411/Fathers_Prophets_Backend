package com.fathersprophets.backend.modules.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class SendResetOtpRequest(
    val username: String,
    val email: String
)