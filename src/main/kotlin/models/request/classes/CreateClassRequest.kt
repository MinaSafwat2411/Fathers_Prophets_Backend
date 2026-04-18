package com.fathersprophets.backend.models.request.classes

import kotlinx.serialization.Serializable

@Serializable
data class CreateClassRequest(
    val name: String,
    val image: String?
)