package com.fathersprophets.backend.modules.comments.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseService
import com.fathersprophets.backend.database.tables.comments.CommentCreateDto
import com.fathersprophets.backend.database.tables.comments.CommentDto
import com.fathersprophets.backend.database.tables.comments.CommentUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.modules.comments.repository.CommentRepository
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class CommentService(
    commentRepository: CommentRepository
) : BaseService<CommentDto, CommentCreateDto, CommentUpdateDto, CommentRepository>(commentRepository), ICommentService {

    override fun getAll(lang: String): ApiResponse<List<CommentDto>> {
        return ApiResponse(
            success = true,
            message = Localization.get("comments_retrieved_success", lang),
            data = repository.getAll()
        )
    }

    override fun getById(id: Int, lang: String): ApiResponse<CommentDto> {
        validateRequired(id to "comment_id", lang = lang)
        val comment = repository.getById(id)
            ?: throw NotFoundException(Localization.get("comment_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("comment_found", lang), data = comment)
    }

    override fun getByUserId(userId: Int, lang: String): ApiResponse<List<CommentDto>> {
        validateRequired(userId to "user_id", lang = lang)
        return ApiResponse(
            success = true,
            message = Localization.get("comments_retrieved_success", lang),
            data = repository.getByUserId(userId)
        )
    }

    override fun getByTeacherId(teacherId: Int, lang: String): ApiResponse<List<CommentDto>> {
        validateRequired(teacherId to "teacher_id", lang = lang)
        return ApiResponse(
            success = true,
            message = Localization.get("comments_retrieved_success", lang),
            data = repository.getByTeacherId(teacherId)
        )
    }

    override fun create(dto: CommentCreateDto, lang: String): ApiResponse<CommentDto> {
        validateRequired(
            dto.userId to "user_id",
            dto.comment to "comment",
            dto.teacherId to "teacher_id",
            lang = lang
        )
        val created = repository.create(dto)
            ?: throw BadRequestException(Localization.get("comment_create_failed", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("comment_create_success", lang),
            data = created
        )
    }

    override fun update(id: Int, dto: CommentUpdateDto, lang: String): ApiResponse<CommentDto> {
        validateRequired(id to "comment_id", lang = lang)
        val updated = repository.update(id, dto)
            ?: throw NotFoundException(Localization.get("comment_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("comment_update_success", lang),
            data = updated
        )
    }

    override fun delete(id: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(id to "comment_id", lang = lang)
        if (!repository.delete(id)) throw NotFoundException(Localization.get("comment_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("comment_delete_success", lang))
    }
}