package com.fathersprophets.backend.models.dto.classes

import kotlinx.serialization.Serializable

@Serializable
data class CreateClassRequest(
    val name: String,
    val image: String?
)