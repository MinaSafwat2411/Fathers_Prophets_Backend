package com.fathersprophets.backend.models.request.users

import kotlinx.serialization.Serializable

@Serializable
data class UpdateEmailRequest(
    val email: String
)
