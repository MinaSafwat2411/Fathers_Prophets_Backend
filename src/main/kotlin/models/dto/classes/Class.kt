package com.fathersprophets.backend.models.dto.classes

import kotlinx.serialization.Serializable

@Serializable
data class Class(
    val id: Int,
    val name: String,
    val image: String?
)