package com.fathersprophets.backend.modules.personstory

import kotlinx.serialization.Serializable

@Serializable
data class PersonStoryDto(
    val id: Int,
    val personId: Int,
    val title: String,
    val content: String,
    val image: String?,
    val video: String?
)

@Serializable
data class PersonStoryCreateDto(
    val personId: Int,
    val title: String,
    val content: String,
    val image: String? = null,
    val video: String? = null
)

@Serializable
data class PersonStoryUpdateDto(
    val personId: Int? = null,
    val title: String? = null,
    val content: String? = null,
    val image: String? = null,
    val video: String? = null
)