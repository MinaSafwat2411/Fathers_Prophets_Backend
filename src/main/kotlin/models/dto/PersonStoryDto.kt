package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.personstory.PersonStoryResponse

data class PersonStoryDto(
    val id: Int,
    val personId: Int,
    val title: String,
    val content: String,
    val image: String?,
    val question: String
) {
    fun convertToPersonStoryResponse() = PersonStoryResponse(
        id = id,
        personId = personId,
        title = title,
        content = content,
        image = image,
        question = question
    )
}
