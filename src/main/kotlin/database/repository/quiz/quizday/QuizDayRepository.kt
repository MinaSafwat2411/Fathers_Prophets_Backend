package com.fathersprophets.backend.database.repository.quiz.quizday

import com.fathersprophets.backend.database.dao.quiz.QuizDayDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizday.CreateQuizDayRequest
import com.fathersprophets.backend.models.quizday.QuizDayResponse
import com.fathersprophets.backend.models.quizday.UpdateQuizDayRequest
import com.fathersprophets.backend.utils.Localization

class QuizDayRepository(
    private val dao: QuizDayDao
) : IQuizDayRepository {

    override fun getAllQuizDays(lang: String): ApiResponse<List<QuizDayResponse>> {
        val quizDays = dao.findAll()
        return ApiResponse(
            success = true,
            data = quizDays.map { it.convertToResponse() },
            message = Localization.get("quiz_days_retrieved_successfully", lang)
        )
    }

    override fun getQuizDaysByQuizId(quizId: Int, lang: String): ApiResponse<List<QuizDayResponse>> {
        val quizDays = dao.findByQuizId(quizId)
        return ApiResponse(
            success = true,
            data = quizDays.map { it.convertToResponse() },
            message = Localization.get("quiz_days_retrieved_successfully", lang)
        )
    }

    override fun createQuizDay(request: CreateQuizDayRequest, lang: String): ApiResponse<QuizDayResponse> {

        val create = dao.create(request.convertToDto())
            ?: throw IllegalArgumentException(Localization.get("quiz_day_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = create.convertToResponse(),
            message = Localization.get("quiz_day_created_successfully", lang)
        )
    }

    override fun updateQuizDay(id: Int, request: UpdateQuizDayRequest, lang: String): ApiResponse<QuizDayResponse> {
        val updated = dao.update(request.convertToDto(id))
            ?:throw IllegalArgumentException(Localization.get("quiz_day_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
            message = Localization.get("quiz_day_updated_successfully", lang)
        )
    }

    override fun deleteQuizDay(id: Int, lang: String): ApiResponse<Nothing> {

        val deleted = dao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("quiz_day_delete_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("quiz_day_deleted_successfully", lang)
        )
    }
}