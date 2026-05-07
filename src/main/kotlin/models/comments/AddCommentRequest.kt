package com.fathersprophets.backend.models.comments

import com.fathersprophets.backend.models.dto.CommentDto
import kotlinx.serialization.Serializable

@Serializable
data class AddCommentRequest(
    val userId : String? = null,
    val comment : String? = null,
){
    fun toCommentDto() = CommentDto(
        id = 0,
        userId = userId?.toIntOrNull() ?: 0,
        comment = comment ?: ""
    )
}
