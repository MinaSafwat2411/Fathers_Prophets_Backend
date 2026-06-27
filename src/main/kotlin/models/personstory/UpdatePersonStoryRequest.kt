package com.fathersprophets.backend.models.personstory

import com.fathersprophets.backend.models.dto.PersonStoryDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePersonStoryRequest(
    val title: String? = null,
    val content: String? = null,
    val image: String? = null,
    val question: String? = null,
){
    fun convertToPersonStoryDto(id: Int, personId: Int) = PersonStoryDto(
        id = id,
        personId = personId,
        title = this.title ?: "",
        content = this.content ?: "",
        image = this.image,
        question = this.question ?: ""
    )
}
