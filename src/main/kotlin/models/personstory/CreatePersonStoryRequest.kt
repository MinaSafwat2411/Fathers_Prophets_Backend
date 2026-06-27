package com.fathersprophets.backend.models.personstory

import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonStoryRequest(
    val personId: Int? = null,
    val title: String? = null,
    val content: String? = null,
    val image: String? = null,
    val question: String? = null
)
