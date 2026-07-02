package com.fathersprophets.backend.services.userprogressquiz

import com.fathersprophets.backend.database.repository.userprogressquiz.IUserProgressQuizRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.userprogressquiz.CreateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UpdateUserProgressQuizRequest
import com.fathersprophets.backend.models.userprogressquiz.UserProgressQuizResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class UserProgressQuizService(
    private val repository: IUserProgressQuizRepository
) : IUserProgressQuizService {

    override fun getAllUserProgress(lang: String): ApiResponse<List<UserProgressQuizResponse>> {
        return repository.getAllUserProgress(lang)
    }

    override fun getUserProgressById(id: Int?, lang: String): ApiResponse<UserProgressQuizResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("user_progress_quiz_id_required", lang))
        return repository.getUserProgressById(id, lang)
    }

    override fun getUserProgressByUserId(userId: Int?, lang: String): ApiResponse<List<UserProgressQuizResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return repository.getUserProgressByUserId(userId, lang)
    }

    override fun getUserProgressByQuizId(quizId: Int?, lang: String): ApiResponse<List<UserProgressQuizResponse>> {
        if (quizId == null) throw IllegalArgumentException(Localization.get("quiz_id_required", lang))
        return repository.getUserProgressByQuizId(quizId, lang)
    }

    override fun getUserProgressByUserIdAndQuizIdAndDayId(userId: Int?, quizId: Int?, dayId: Int?, lang: String): ApiResponse<UserProgressQuizResponse> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        if (quizId == null) throw IllegalArgumentException(Localization.get("quiz_id_required", lang))
        if (dayId == null) throw IllegalArgumentException(Localization.get("quiz_day_id_required", lang))
        return repository.getUserProgressByUserIdAndQuizIdAndDayId(userId, quizId, dayId, lang)
    }

    override fun createUserProgress(request: CreateUserProgressQuizRequest, lang: String): ApiResponse<UserProgressQuizResponse> {
        validateRequired(
            request.userId to "userId",
            request.quizId to "quizId",
            request.dayId to "dayId",
            lang = lang
        )
        return repository.createUserProgress(request, lang)
    }

    override fun updateUserProgress(id: Int?, request: UpdateUserProgressQuizRequest, lang: String): ApiResponse<UserProgressQuizResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("user_progress_quiz_id_required", lang))
        validateRequired(
            request.userId to "userId",
            request.quizId to "quizId",
            request.dayId to "dayId",
            lang = lang
        )
        return repository.updateUserProgress(id, request, lang)
    }

    override fun deleteUserProgress(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("user_progress_quiz_id_required", lang))
        return repository.deleteUserProgress(id, lang)
    }
}