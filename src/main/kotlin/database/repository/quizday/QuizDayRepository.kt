package com.fathersprophets.backend.database.repository.quizday

import com.fathersprophets.backend.database.dao.quiz.QuizDayDao
import com.fathersprophets.backend.database.tables.quiz.DayOfWeek
import com.fathersprophets.backend.database.tables.quiz.QuizDayType
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.QuizDayDto
import com.fathersprophets.backend.models.quizday.CreateQuizDayRequest
import com.fathersprophets.backend.models.quizday.QuizDayResponse
import com.fathersprophets.backend.models.quizday.UpdateQuizDayRequest
import com.fathersprophets.backend.utils.Localization
import java.time.Instant

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

    override fun getQuizDayById(id: Int, lang: String): ApiResponse<QuizDayResponse> {
        val quizDay = dao.findById(id)
        return ApiResponse(
            success = true,
            data = quizDay?.convertToResponse(),
            message = Localization.get("quiz_day_retrieved_successfully", lang)
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
        val id = dao.create(request.convertToDto())
        val created = dao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("quiz_day_created_successfully", lang)
        )
    }

    override fun updateQuizDay(id: Int, request: UpdateQuizDayRequest, lang: String): ApiResponse<QuizDayResponse> {
        dao.update(request.convertToDto(id))
        val updated = dao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("quiz_day_updated_successfully", lang)
        )
    }

    override fun deleteQuizDay(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("quiz_day_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = QuizDayDto(
        id = id,
        quizId = 0,
        dayName = DayOfWeek.SAT,
        startAt = Instant.EPOCH,
        endAt = Instant.EPOCH,
        book = "",
        chapter = 0,
        verseFrom = 0,
        verseTo = 0,
        typeDay = QuizDayType.TRUE_FALSE
    )
}