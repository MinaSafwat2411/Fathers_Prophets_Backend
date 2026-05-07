package com.fathersprophets.backend.models.comments

import com.fathersprophets.backend.models.dto.CommentDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCommentRequest(
    val commentId: Int? = null,
    val comment: String? = null,
    val userId: Int? = null
){
    fun toCommentDto() = CommentDto(
        id = commentId ?: 0,
        userId = userId ?: 0,
        comment = comment ?: ""
    )
}
