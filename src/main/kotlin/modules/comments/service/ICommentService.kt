package com.fathersprophets.backend.modules.comments.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.database.tables.comments.CommentCreateDto
import com.fathersprophets.backend.database.tables.comments.CommentDto
import com.fathersprophets.backend.database.tables.comments.CommentUpdateDto

interface ICommentService {
    fun getAll(lang: String): ApiResponse<List<CommentDto>>
    fun getById(id: Int, lang: String): ApiResponse<CommentDto>
    fun getByUserId(userId: Int, lang: String): ApiResponse<List<CommentDto>>
    fun getByTeacherId(teacherId: Int, lang: String): ApiResponse<List<CommentDto>>
    fun create(dto: CommentCreateDto, lang: String): ApiResponse<CommentDto>
    fun update(id: Int, dto: CommentUpdateDto, lang: String): ApiResponse<CommentDto>
    fun delete(id: Int, lang: String): ApiResponse<Nothing>
}