package com.fathersprophets.backend.database.tables.comments

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: Int,
    val userId: Int,
    val comment: String,
    val teacherId: Int
)

@Serializable
data class CommentCreateDto(
    val userId: Int,
    val comment: String,
    val teacherId: Int
)

@Serializable
data class CommentUpdateDto(
    val comment: String? = null,
    val teacherId: Int? = null
)