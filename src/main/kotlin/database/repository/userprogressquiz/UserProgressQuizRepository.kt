package com.fathersprophets.backend.database.repository.userprogressquiz

import com.fathersprophets.backend.database.dao.users.UserProgressQuizDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.UserProgressQuizDto
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

    override fun getUserProgressById(id: Int, lang: String): ApiResponse<UserProgressQuizResponse> {
        val progress = dao.findById(id)
        return ApiResponse(
            success = true,
            data = progress?.convertToResponse(),
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

    override fun getUserProgressByQuizId(quizId: Int, lang: String): ApiResponse<List<UserProgressQuizResponse>> {
        val progress = dao.findByQuizId(quizId)
        return ApiResponse(
            success = true,
            data = progress.map { it.convertToResponse() },
            message = Localization.get("user_progress_quiz_retrieved_successfully", lang)
        )
    }

    override fun getUserProgressByUserIdAndQuizIdAndDayId(userId: Int, quizId: Int, dayId: Int, lang: String): ApiResponse<UserProgressQuizResponse> {
        val progress = dao.findByUserIdAndQuizIdAndDayId(userId, quizId, dayId)
        return ApiResponse(
            success = true,
            data = progress?.convertToResponse(),
            message = Localization.get("user_progress_quiz_retrieved_successfully", lang)
        )
    }

    override fun createUserProgress(request: CreateUserProgressQuizRequest, lang: String): ApiResponse<UserProgressQuizResponse> {
        val existing = dao.findByUserIdAndQuizIdAndDayId(request.userId, request.quizId, request.dayId)
        if (existing != null) throw IllegalStateException(Localization.get("user_progress_quiz_already_exists", lang))

        val id = dao.create(request.convertToDto())
        val created = dao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("user_progress_quiz_created_successfully", lang)
        )
    }

    override fun updateUserProgress(id: Int, request: UpdateUserProgressQuizRequest, lang: String): ApiResponse<UserProgressQuizResponse> {
        dao.update(request.convertToDto(id))
        val updated = dao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("user_progress_quiz_updated_successfully", lang)
        )
    }

    override fun deleteUserProgress(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("user_progress_quiz_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = UserProgressQuizDto(
        id = id,
        userId = 0,
        quizId = 0,
        dayId = 0,
        score = 0
    )
}