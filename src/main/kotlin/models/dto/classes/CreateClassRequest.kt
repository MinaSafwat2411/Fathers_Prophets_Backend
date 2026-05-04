package com.fathersprophets.backend.models.dto.classes

import kotlinx.serialization.Serializable

@Serializable
data class CreateClassRequest(
    val name: String? = null,
    val image: String? = null
)