package com.fathersprophets.backend.modules.quiz.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseService
import com.fathersprophets.backend.database.tables.quiz.QuizCreateDto
import com.fathersprophets.backend.database.tables.quiz.QuizDto
import com.fathersprophets.backend.database.tables.quiz.QuizUpdateDto
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.ConflictException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.modules.quiz.repository.QuizRepository
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired
import java.time.Instant
import java.time.format.DateTimeParseException

class QuizService(
    quizRepository: QuizRepository
) : BaseService<QuizDto, QuizCreateDto, QuizUpdateDto, QuizRepository>(quizRepository), IQuizService {

    override fun getAll(lang: String): ApiResponse<List<QuizDto>> {
        return ApiResponse(
            success = true,
            message = Localization.get("quizzes_retrieved_successfully", lang),
            data = repository.getAll()
        )
    }

    override fun getById(id: Int, lang: String): ApiResponse<QuizDto> {
        validateRequired(id to "quiz_id", lang = lang)
        val quiz = repository.getById(id) ?: throw NotFoundException(Localization.get("quiz_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_retrieved_successfully", lang),
            data = quiz
        )
    }

    override fun getByNumber(number: Int, lang: String): ApiResponse<QuizDto> {
        validateRequired(number to "quiz_number", lang = lang)
        val quiz = repository.getByNumber(number) ?: throw NotFoundException(Localization.get("quiz_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_retrieved_successfully", lang),
            data = quiz
        )
    }

    override fun getByFamilyId(familyId: Int, lang: String): ApiResponse<List<QuizDto>> {
        validateRequired(familyId to "family_id", lang = lang)
        return ApiResponse(
            success = true,
            message = Localization.get("quizzes_retrieved_successfully", lang),
            data = repository.getByFamilyId(familyId)
        )
    }

    override fun create(dto: QuizCreateDto, lang: String): ApiResponse<QuizDto> {
        validateRequired(
            dto.number to "quiz_number",
            dto.startAt to "start_at",
            dto.endAt to "end_at",
            dto.title to "title",
            dto.familyId to "family_id",
            lang = lang
        )
        assertDateRange(parseInstant(dto.startAt, lang), parseInstant(dto.endAt, lang), lang)
        assertNumberFree(dto.number, currentId = null, lang = lang)

        val created = repository.create(dto)
            ?: throw BadRequestException(Localization.get("quiz_create_failed", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_created_successfully", lang),
            data = created
        )
    }

    override fun update(id: Int, dto: QuizUpdateDto, lang: String): ApiResponse<QuizDto> {
        validateRequired(id to "quiz_id", lang = lang)
        val existing = repository.getById(id) ?: throw NotFoundException(Localization.get("quiz_not_found", lang))

        // A partial update may move only one end of the range, so compare against the stored value
        // for whichever side wasn't supplied.
        assertDateRange(
            start = dto.startAt?.let { parseInstant(it, lang) } ?: parseInstant(existing.startAt, lang),
            end = dto.endAt?.let { parseInstant(it, lang) } ?: parseInstant(existing.endAt, lang),
            lang = lang
        )
        dto.number?.let { assertNumberFree(it, currentId = id, lang = lang) }

        val updated = repository.update(id, dto) ?: throw NotFoundException(Localization.get("quiz_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_updated_successfully", lang),
            data = updated
        )
    }

    override fun delete(id: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(id to "quiz_id", lang = lang)
        if (!repository.delete(id)) throw NotFoundException(Localization.get("quiz_not_found", lang))
        return ApiResponse(success = true, message = Localization.get("quiz_deleted_successfully", lang))
    }

    private fun assertDateRange(start: Instant, end: Instant, lang: String) {
        if (!start.isBefore(end)) {
            throw BadRequestException(Localization.get("quiz_date_range_invalid", lang))
        }
    }

    // QuizTable.number is a unique index; check it here so a clash reads as a 409.
    private fun assertNumberFree(number: Int, currentId: Int?, lang: String) {
        val existing = repository.getByNumber(number)
        if (existing != null && existing.id != currentId) {
            throw ConflictException(Localization.get("quiz_number_exists", lang))
        }
    }

    // The DAO calls Instant.parse directly, which would surface a malformed date as a 500.
    private fun parseInstant(value: String, lang: String): Instant =
        try {
            Instant.parse(value)
        } catch (_: DateTimeParseException) {
            throw BadRequestException(Localization.get("invalid_date_format", lang))
        }
}