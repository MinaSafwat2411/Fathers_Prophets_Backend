package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.comments.CommentResponse

data class CommentDto(
    val id : Int,
    val userId : Int,
    val comment : String,
){
    fun convertToCommentResponse() = CommentResponse(
        id = id,
        userId = userId,
        comment = comment
    )
}
