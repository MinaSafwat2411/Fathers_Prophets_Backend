package com.fathersprophets.backend.modules.comments.repository

import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.database.tables.comments.CommentCreateDto
import com.fathersprophets.backend.database.tables.comments.CommentDao
import com.fathersprophets.backend.database.tables.comments.CommentDto
import com.fathersprophets.backend.database.tables.comments.CommentUpdateDto

class CommentRepository(
    commentDao: CommentDao
) : BaseRepository<CommentDto, CommentCreateDto, CommentUpdateDto, CommentDao>(commentDao), ICommentRepository {

    override fun getByUserId(userId: Int): List<CommentDto> = dao.getByUserId(userId)

    override fun getByTeacherId(teacherId: Int): List<CommentDto> = dao.getByTeacherId(teacherId)
}