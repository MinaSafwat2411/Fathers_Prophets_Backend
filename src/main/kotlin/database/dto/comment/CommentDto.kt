package com.fathersprophets.backend.database.dto.comment

data class CommentDto(
    val id : Int,
    val userId : Int,
    val comment : String,
)
