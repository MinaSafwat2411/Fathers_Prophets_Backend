package com.fathersprophets.backend.modules.comments.repository

import com.fathersprophets.backend.database.tables.comments.CommentDto

interface ICommentRepository {
    fun getByUserId(userId: Int): List<CommentDto>
    fun getByTeacherId(teacherId: Int): List<CommentDto>
}