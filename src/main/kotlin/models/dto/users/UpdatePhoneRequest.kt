package com.fathersprophets.backend.models.dto.users

import kotlinx.serialization.Serializable


@Serializable
data class UpdatePhoneRequest(
    val phone: String? = null
)
