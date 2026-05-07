package com.fathersprophets.backend.models.comments

import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    val id : Int,
    val userId : Int,
    val comment : String,
)
