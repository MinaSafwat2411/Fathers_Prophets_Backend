package com.fathersprophets.backend.models.personstory

import com.fathersprophets.backend.models.dto.PersonStoryDto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePersonStoryRequest(
    val personId: Int? = null,
    val title: String? = null,
    val content: String? = null,
    val image: String? = null,
    val video: String? = null
){
    fun convertToDto() = PersonStoryDto(
        id = 0,
        personId = personId ?: 0,
        title = title ?: "",
        content = content ?: "",
        image = image,
        video = video
    )
}
