package com.fathersprophets.backend.services.quiz

import com.fathersprophets.backend.database.repository.quiz.IQuizRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quiz.CreateQuizRequest
import com.fathersprophets.backend.models.quiz.QuizResponse
import com.fathersprophets.backend.models.quiz.UpdateQuizRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired
import java.time.Instant

class QuizService(
    private val repository: IQuizRepository
) : IQuizService {

    override fun getAllQuizzes(lang: String): ApiResponse<List<QuizResponse>> {
        return repository.getAllQuizzes(lang)
    }

    override fun getQuizById(id: Int?, lang: String): ApiResponse<QuizResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("quiz_id_required", lang))
        return repository.getQuizById(id, lang)
    }

    override fun createQuiz(request: CreateQuizRequest, lang: String): ApiResponse<Int> {
        validateRequired(
            request.number to "number",
            request.startAt to "startAt",
            request.endAt to "endAt",
            lang = lang
        )
        validateDateRange(request.startAt, request.endAt, lang)
        return repository.createQuiz(request, lang)
    }

    override fun updateQuiz(id: Int?, request: UpdateQuizRequest, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("quiz_id_required", lang))
        validateRequired(
            request.number to "number",
            request.startAt to "startAt",
            request.endAt to "endAt",
            lang = lang
        )
        validateDateRange(request.startAt, request.endAt, lang)
        return repository.updateQuiz(id, request, lang)
    }

    override fun deleteQuiz(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("quiz_id_required", lang))
        return repository.deleteQuiz(id, lang)
    }

    private fun validateDateRange(startAt: String, endAt: String, lang: String) {
        if (!Instant.parse(startAt).isBefore(Instant.parse(endAt))) {
            throw IllegalArgumentException(Localization.get("quiz_date_range_invalid", lang))
        }
    }
}