package com.fathersprophets.backend.models.response.classes

import kotlinx.serialization.Serializable

@Serializable
data class ClassResponse(
    val id: Int,
    val name: String,
    val image: String?
)
