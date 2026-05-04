package com.fathersprophets.backend.models.request.users

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val profile: String
)