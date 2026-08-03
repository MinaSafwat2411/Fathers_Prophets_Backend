package com.fathersprophets.backend.models.comments

import com.fathersprophets.backend.database.dto.comments.CommentDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCommentRequest(
    val comment: String? = null,
    val userId: Int? = null
){
    fun toCommentDto(id : Int) = CommentDto(
        id = id,
        userId = userId ?: 0,
        comment = comment ?: ""
    )
}
