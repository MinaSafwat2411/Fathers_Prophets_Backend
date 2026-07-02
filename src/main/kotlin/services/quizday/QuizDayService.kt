package com.fathersprophets.backend.services.quizday

import com.fathersprophets.backend.database.repository.quizday.IQuizDayRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizday.CreateQuizDayRequest
import com.fathersprophets.backend.models.quizday.QuizDayResponse
import com.fathersprophets.backend.models.quizday.UpdateQuizDayRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired
import java.time.Instant

class QuizDayService(
    private val repository: IQuizDayRepository
) : IQuizDayService {

    override fun getAllQuizDays(lang: String): ApiResponse<List<QuizDayResponse>> {
        return repository.getAllQuizDays(lang)
    }

    override fun getQuizDayById(id: Int?, lang: String): ApiResponse<QuizDayResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("quiz_day_id_required", lang))
        return repository.getQuizDayById(id, lang)
    }

    override fun getQuizDaysByQuizId(quizId: Int?, lang: String): ApiResponse<List<QuizDayResponse>> {
        if (quizId == null) throw IllegalArgumentException(Localization.get("quiz_id_required", lang))
        return repository.getQuizDaysByQuizId(quizId, lang)
    }

    override fun createQuizDay(request: CreateQuizDayRequest, lang: String): ApiResponse<QuizDayResponse> {
        validateRequired(
            request.quizId to "quizId",
            request.dayName to "dayName",
            request.startAt to "startAt",
            request.endAt to "endAt",
            request.book to "book",
            request.chapter to "chapter",
            request.verseFrom to "verseFrom",
            request.verseTo to "verseTo",
            request.typeDay to "typeDay",
            lang = lang
        )
        validateDateRange(request.startAt, request.endAt, lang)
        validateVerseRange(request.verseFrom, request.verseTo, lang)
        return repository.createQuizDay(request, lang)
    }

    override fun updateQuizDay(id: Int?, request: UpdateQuizDayRequest, lang: String): ApiResponse<QuizDayResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("quiz_day_id_required", lang))
        validateRequired(
            request.quizId to "quizId",
            request.dayName to "dayName",
            request.startAt to "startAt",
            request.endAt to "endAt",
            request.book to "book",
            request.chapter to "chapter",
            request.verseFrom to "verseFrom",
            request.verseTo to "verseTo",
            request.typeDay to "typeDay",
            lang = lang
        )
        validateDateRange(request.startAt, request.endAt, lang)
        validateVerseRange(request.verseFrom, request.verseTo, lang)
        return repository.updateQuizDay(id, request, lang)
    }

    override fun deleteQuizDay(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("quiz_day_id_required", lang))
        return repository.deleteQuizDay(id, lang)
    }

    private fun validateDateRange(startAt: String, endAt: String, lang: String) {
        if (!Instant.parse(startAt).isBefore(Instant.parse(endAt))) {
            throw IllegalArgumentException(Localization.get("quiz_day_date_range_invalid", lang))
        }
    }

    private fun validateVerseRange(verseFrom: Int, verseTo: Int, lang: String) {
        if (verseFrom > verseTo) {
            throw IllegalArgumentException(Localization.get("quiz_day_verse_range_invalid", lang))
        }
    }
}