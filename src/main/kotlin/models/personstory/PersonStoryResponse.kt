package com.fathersprophets.backend.models.personstory

import kotlinx.serialization.Serializable

@Serializable
data class PersonStoryResponse(
    val id: Int,
    val personId: Int,
    val title: String,
    val content: String,
    val image: String?,
    val video: String?
)
