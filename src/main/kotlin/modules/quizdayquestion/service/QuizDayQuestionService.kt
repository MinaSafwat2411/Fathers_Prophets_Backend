package com.fathersprophets.backend.modules.quizdayquestion.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.base.BaseService
import com.fathersprophets.backend.database.enums.McqCorrectAnswer
import com.fathersprophets.backend.exceptions.BadRequestException
import com.fathersprophets.backend.exceptions.NotFoundException
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionCreateDto
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionDto
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionUpdateDto
import com.fathersprophets.backend.modules.quizdayquestion.repository.QuizDayQuestionRepository
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class QuizDayQuestionService(
    quizDayQuestionRepository: QuizDayQuestionRepository
) : BaseService<QuizDayQuestionDto, QuizDayQuestionCreateDto, QuizDayQuestionUpdateDto, QuizDayQuestionRepository>(
    quizDayQuestionRepository
), IQuizDayQuestionService {

    override fun getAll(lang: String): ApiResponse<List<QuizDayQuestionDto>> {
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_questions_retrieved_successfully", lang),
            data = repository.getAll()
        )
    }

    override fun getById(id: Int, lang: String): ApiResponse<QuizDayQuestionDto> {
        validateRequired(id to "quiz_day_question_id", lang = lang)
        val question = repository.getById(id)
            ?: throw NotFoundException(Localization.get("quiz_day_question_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_question_retrieved_successfully", lang),
            data = question
        )
    }

    override fun getByQuizDayId(quizDayId: Int, lang: String): ApiResponse<List<QuizDayQuestionDto>> {
        validateRequired(quizDayId to "quiz_day_id", lang = lang)
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_questions_retrieved_successfully", lang),
            data = repository.getByQuizDayId(quizDayId)
        )
    }

    override fun create(dto: QuizDayQuestionCreateDto, lang: String): ApiResponse<QuizDayQuestionDto> {
        validateCreate(dto, lang)

        val created = repository.create(dto)
            ?: throw BadRequestException(Localization.get("quiz_day_question_create_failed", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_question_created_successfully", lang),
            data = created
        )
    }

    override fun createAll(
        dtos: List<QuizDayQuestionCreateDto>,
        lang: String
    ): ApiResponse<List<QuizDayQuestionDto>> {
        if (dtos.isEmpty()) {
            throw BadRequestException(Localization.get("quiz_day_questions_required", lang))
        }
        // Validate the whole batch before writing any of it, so a bad entry halfway down
        // doesn't leave the earlier ones persisted.
        dtos.forEach { validateCreate(it, lang) }

        val created = repository.createAll(dtos)
        if (created.size != dtos.size) {
            throw BadRequestException(Localization.get("quiz_day_question_create_failed", lang))
        }
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_questions_created_successfully", lang),
            data = created
        )
    }

    override fun update(id: Int, dto: QuizDayQuestionUpdateDto, lang: String): ApiResponse<QuizDayQuestionDto> {
        validateRequired(id to "quiz_day_question_id", lang = lang)
        val existing = repository.getById(id)
            ?: throw NotFoundException(Localization.get("quiz_day_question_not_found", lang))

        // A partial update can change the answer or the choices independently, so check the
        // combination that will actually be stored.
        assertCorrectAnswerHasChoice(
            correctAnswer = dto.correctAnswer ?: existing.correctAnswer,
            choice3 = dto.choice3 ?: existing.choice3,
            choice4 = dto.choice4 ?: existing.choice4,
            lang = lang
        )

        val updated = repository.update(id, dto)
            ?: throw NotFoundException(Localization.get("quiz_day_question_not_found", lang))
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_question_updated_successfully", lang),
            data = updated
        )
    }

    override fun delete(id: Int, lang: String): ApiResponse<Nothing> {
        validateRequired(id to "quiz_day_question_id", lang = lang)
        if (!repository.delete(id)) {
            throw NotFoundException(Localization.get("quiz_day_question_not_found", lang))
        }
        return ApiResponse(
            success = true,
            message = Localization.get("quiz_day_question_deleted_successfully", lang)
        )
    }

    private fun validateCreate(dto: QuizDayQuestionCreateDto, lang: String) {
        validateRequired(
            dto.quizDayId to "quiz_day_id",
            dto.question to "question",
            dto.choice1 to "choice_1",
            dto.choice2 to "choice_2",
            dto.correctAnswer to "correct_answer",
            lang = lang
        )
        assertCorrectAnswerHasChoice(dto.correctAnswer, dto.choice3, dto.choice4, lang)
    }

    // choice3/choice4 are optional, so a question can't point its answer at a choice it doesn't have.
    private fun assertCorrectAnswerHasChoice(
        correctAnswer: McqCorrectAnswer,
        choice3: String?,
        choice4: String?,
        lang: String
    ) {
        val answerHasNoChoice = when (correctAnswer) {
            McqCorrectAnswer.Third -> choice3.isNullOrBlank()
            McqCorrectAnswer.Fourth -> choice4.isNullOrBlank()
            McqCorrectAnswer.First, McqCorrectAnswer.Second -> false
        }
        if (answerHasNoChoice) {
            throw BadRequestException(Localization.get("quiz_day_question_correct_answer_invalid", lang))
        }
    }
}