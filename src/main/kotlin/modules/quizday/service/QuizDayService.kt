package com.fathersprophets.backend.modules.quizday.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseService
import com.fathersprophets.backend.database.enums.DayOfWeek
import com.fathersprophets.backend.database.tables.quizday.QuizDayCreateDto
import com.fathersprophets.backend.database.tables.quizday.QuizDayDto
import com.fathersprophets.backend.database.tables.quizday.QuizDayUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.modules.quizday.repository.QuizDayRepository
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class QuizDayService(
    quizDayRepository: QuizDayRepository
) : BaseService<QuizDayDto, QuizDayCreateDto, QuizDayUpdateDto, QuizDayRepository>(quizDayRepository), IQuizDayService {

    override fun getAll(lang: String): ApiResponse<List<QuizDayDto>> {
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_days_retrieved_successfully", lang),
            data = repository.getAll()
        )
    }

    override fun getById(id: Int, lang: String): ApiResponse<QuizDayDto> {
        validateRequired(id to "quiz_day_id", lang = lang)
        val quizDay = repository.getById(id) ?: throw NotFoundException(Localization.get("quiz_day_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_retrieved_successfully", lang),
            data = quizDay
        )
    }

    override fun getByQuizId(quizId: Int, lang: String): ApiResponse<List<QuizDayDto>> {
        validateRequired(quizId to "quiz_id", lang = lang)
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_days_retrieved_successfully", lang),
            data = repository.getByQuizId(quizId)
        )
    }

    override fun getByQuizAndDay(quizId: Int, dayName: DayOfWeek, lang: String): ApiResponse<QuizDayDto> {
        validateRequired(quizId to "quiz_id", lang = lang)
        val quizDay = repository.getByQuizAndDay(quizId, dayName)
            ?: throw NotFoundException(Localization.get("quiz_day_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_retrieved_successfully", lang),
            data = quizDay
        )
    }

    override fun create(dto: QuizDayCreateDto, lang: String): ApiResponse<QuizDayDto> {
        validateRequired(
            dto.quizId to "quiz_id",
            dto.dayName to "day_name",
            dto.book to "book",
            dto.chapter to "chapter",
            dto.verseFrom to "verse_from",
            dto.verseTo to "verse_to",
            dto.typeDay to "type_day",
            lang = lang
        )
        assertVerseRange(dto.verseFrom, dto.verseTo, lang)
        assertDayFree(dto.quizId, dto.dayName, currentId = null, lang = lang)

        val created = repository.create(dto)
            ?: throw BadRequestException(Localization.get("quiz_day_create_failed", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_created_successfully", lang),
            data = created
        )
    }

    override fun update(id: Int, dto: QuizDayUpdateDto, lang: String): ApiResponse<QuizDayDto> {
        validateRequired(id to "quiz_day_id", lang = lang)
        val existing = repository.getById(id) ?: throw NotFoundException(Localization.get("quiz_day_not_found", lang))

        // A partial update may move only one end of the verse range, so fall back to the stored value.
        assertVerseRange(
            verseFrom = dto.verseFrom ?: existing.verseFrom,
            verseTo = dto.verseTo ?: existing.verseTo,
            lang = lang
        )
        if (dto.quizId != null || dto.dayName != null) {
            assertDayFree(
                quizId = dto.quizId ?: existing.quizId,
                dayName = dto.dayName ?: existing.dayName,
                currentId = id,
                lang = lang
            )
        }

        val updated = repository.update(id, dto)
            ?: throw NotFoundException(Localization.get("quiz_day_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_updated_successfully", lang),
            data = updated
        )
    }

    override fun delete(id: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(id to "quiz_day_id", lang = lang)
        if (!repository.delete(id)) throw NotFoundException(Localization.get("quiz_day_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("quiz_day_deleted_successfully", lang))
    }

    private fun assertVerseRange(verseFrom: Int, verseTo: Int, lang: String) {
        if (verseFrom > verseTo) {
            throw BadRequestException(Localization.get("quiz_day_verse_range_invalid", lang))
        }
    }

    // QuizDayTable has a unique (quiz_id, day_name) index — one entry per day per quiz.
    private fun assertDayFree(quizId: Int, dayName: DayOfWeek, currentId: Int?, lang: String) {
        val existing = repository.getByQuizAndDay(quizId, dayName)
        if (existing != null && existing.id != currentId) {
            throw ConflictException(Localization.get("quiz_day_already_exists", lang))
        }
    }
}