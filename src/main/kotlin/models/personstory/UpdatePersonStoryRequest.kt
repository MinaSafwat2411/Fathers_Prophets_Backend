package com.fathersprophets.backend.models.personstory

import com.fathersprophets.backend.models.dto.PersonStoryDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonStoryRequest(
    val title: String? = null,
    val content: String? = null,
    val image: String? = null,
    val video: String? = null,
    val personId: Int? = null
){
    fun convertToPersonStoryDto(id: Int) = PersonStoryDto(
        id = id,
        personId = this.personId ?: 0,
        title = this.title ?: "",
        content = this.content ?: "",
        image = this.image,
        video = this.video
    )
}
