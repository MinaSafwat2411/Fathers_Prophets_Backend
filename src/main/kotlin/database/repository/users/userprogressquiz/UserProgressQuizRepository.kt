package com.fathersprophets.backend.database.repository.userprogressquiz

import com.fathersprophets.backend.database.dao.users.UserProgressQuizDao
import com.fathersprophets.backend.database.repository.users.userprogressquiz.IUserProgressQuizRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.userprogressquiz.CreateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UpdateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UserProgressQuizResponse
import com.fathersprophets.backend.utils.Localization

class UserProgressQuizRepository(
    private val dao: UserProgressQuizDao
) : IUserProgressQuizRepository {

    override fun getAllUserProgress(lang: String): ApiResponse<List<UserProgressQuizResponse>> {
        val progress = dao.findAll()
        return ApiResponse(
            success = true,
            data = progress.map { it.convertToResponse() },
            message = Localization.get("user_progress_quiz_retrieved_successfully", lang)
        )
    }

    override fun getUserProgressByUserId(userId: Int, lang: String): ApiResponse<List<UserProgressQuizResponse>> {
        val progress = dao.findByUserId(userId)
        return ApiResponse(
            success = true,
            data = progress.map { it.convertToResponse() },
            message = Localization.get("user_progress_quiz_retrieved_successfully", lang)
        )
    }

    override fun createUserProgress(request: CreateUserProgressQuizRequest, lang: String): ApiResponse<UserProgressQuizResponse> {
        val create = dao.create(request.convertToDto())
            ?:throw IllegalArgumentException(Localization.get("user_progress_quiz_creation_failed", lang))
        return ApiResponse(
            success = true,
            data = create.convertToResponse(),
            message = Localization.get("user_progress_quiz_created_successfully", lang)
        )
    }

    override fun updateUserProgress(id: Int, request: UpdateUserProgressQuizRequest, lang: String): ApiResponse<UserProgressQuizResponse> {

        val updated = dao.update(request.convertToDto(id))
            ?:throw IllegalArgumentException(Localization.get("user_progress_quiz_update_failed", lang))
        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
            message = Localization.get("user_progress_quiz_updated_successfully", lang)
        )
    }

    override fun deleteUserProgress(id: Int, lang: String): ApiResponse<Nothing> {
        val deleted = dao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("user_progress_quiz_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("user_progress_quiz_deleted_successfully", lang)
        )
    }
}