package com.fathersprophets.backend.database.repository.quizanswer

import com.fathersprophets.backend.database.dao.QuizAnswerDao
import com.fathersprophets.backend.database.dao.QuizDayQuestionDao
import com.fathersprophets.backend.database.dao.UserProgressQuizDao
import com.fathersprophets.backend.database.tables.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.QuizAnswerDto
import com.fathersprophets.backend.models.quizanswer.CreateQuizAnswerRequest
import com.fathersprophets.backend.models.quizanswer.QuizAnswerResponse
import com.fathersprophets.backend.models.quizanswer.UpdateQuizAnswerRequest
import com.fathersprophets.backend.utils.Localization

class QuizAnswerRepository(
    private val dao: QuizAnswerDao,
    private val quizDayQuestionDao: QuizDayQuestionDao,
    private val userProgressQuizDao: UserProgressQuizDao
) : IQuizAnswerRepository {

    override fun getAllQuizAnswers(lang: String): ApiResponse<List<QuizAnswerResponse>> {
        val answers = dao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("quiz_answers_retrieved_successfully", lang)
        )
    }

    override fun getQuizAnswerById(id: Int, lang: String): ApiResponse<QuizAnswerResponse> {
        val answer = dao.findById(id)
        return ApiResponse(
            success = true,
            data = answer?.convertToResponse(),
            message = Localization.get("quiz_answer_retrieved_successfully", lang)
        )
    }

    override fun getQuizAnswersByQuestionId(questionId: Int, lang: String): ApiResponse<List<QuizAnswerResponse>> {
        val answers = dao.findByQuestionId(questionId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("quiz_answers_retrieved_successfully", lang)
        )
    }

    override fun getQuizAnswersByUserId(userId: Int, lang: String): ApiResponse<List<QuizAnswerResponse>> {
        val answers = dao.findByUserId(userId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("quiz_answers_retrieved_successfully", lang)
        )
    }

    override fun getQuizAnswersByDayId(dayId: Int, lang: String): ApiResponse<List<QuizAnswerResponse>> {
        val answers = dao.findByDayId(dayId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("quiz_answers_retrieved_successfully", lang)
        )
    }

    override fun getQuizAnswersByQuizId(quizId: Int, lang: String): ApiResponse<List<QuizAnswerResponse>> {
        val answers = dao.findByQuizId(quizId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("quiz_answers_retrieved_successfully", lang)
        )
    }

    override fun createQuizAnswer(request: CreateQuizAnswerRequest, lang: String): ApiResponse<QuizAnswerResponse> {
        val dto = buildDtoForCreate(request, lang)

        val id = dao.create(dto)
        trackScore(dto)
        val created = dao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("quiz_answer_created_successfully", lang)
        )
    }

    override fun createQuizAnswers(requests: List<CreateQuizAnswerRequest>, lang: String): ApiResponse<List<QuizAnswerResponse>> {
        val seen = mutableSetOf<List<Int>>()
        val dtos = requests.map { request ->
            val key = listOf(request.questionId, request.userId, request.dayId, request.quizId)
            if (!seen.add(key)) throw IllegalStateException(Localization.get("quiz_answer_already_exists", lang))
            buildDtoForCreate(request, lang)
        }

        val created = dao.createMany(dtos)
        created.forEach { trackScore(it) }
        return ApiResponse(
            success = true,
            data = created.map { it.convertToResponse() },
            message = Localization.get("quiz_answers_created_successfully", lang)
        )
    }

    private fun buildDtoForCreate(request: CreateQuizAnswerRequest, lang: String): QuizAnswerDto {
        val existing = dao.findByQuestionIdAndUserIdAndDayIdAndQuizId(
            request.questionId, request.userId, request.dayId, request.quizId
        )
        if (existing != null) throw IllegalStateException(Localization.get("quiz_answer_already_exists", lang))

        val question = quizDayQuestionDao.findById(request.questionId)
            ?: throw IllegalArgumentException(Localization.get("quiz_day_question_not_found", lang))

        return QuizAnswerDto(
            id = 0,
            quizId = request.quizId,
            questionId = request.questionId,
            dayId = request.dayId,
            userId = request.userId,
            answer = request.answer,
            status = autoGrade(request.answer, question.correctAnswer.name)
        )
    }

    override fun updateQuizAnswer(id: Int, request: UpdateQuizAnswerRequest, lang: String): ApiResponse<QuizAnswerResponse> {
        val previous = dao.findById(id)

        val question = quizDayQuestionDao.findById(request.questionId)
            ?: throw IllegalArgumentException(Localization.get("quiz_day_question_not_found", lang))

        val status = autoGrade(request.answer, question.correctAnswer.name)

        dao.update(
            QuizAnswerDto(
                id = id,
                quizId = request.quizId,
                questionId = request.questionId,
                dayId = request.dayId,
                userId = request.userId,
                answer = request.answer,
                status = status
            )
        )

        val wasCorrect = previous?.status == AnswerStatus.IS_TRUE
        val isCorrect = status == AnswerStatus.IS_TRUE
        if (wasCorrect != isCorrect) {
            userProgressQuizDao.incrementScore(request.userId, request.quizId, request.dayId, if (isCorrect) 1 else -1)
        }

        val updated = dao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("quiz_answer_updated_successfully", lang)
        )
    }

    override fun deleteQuizAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        val existing = dao.findById(id)
        dao.delete(idToDto(id))
        if (existing?.status == AnswerStatus.IS_TRUE) {
            userProgressQuizDao.incrementScore(existing.userId, existing.quizId, existing.dayId, -1)
        }
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("quiz_answer_deleted_successfully", lang)
        )
    }

    private fun trackScore(dto: QuizAnswerDto) {
        if (dto.status == AnswerStatus.IS_TRUE) {
            userProgressQuizDao.incrementScore(dto.userId, dto.quizId, dto.dayId, 1)
        }
    }

    private fun autoGrade(answer: String, correctAnswer: String) =
        if (answer == correctAnswer) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE

    private fun idToDto(id: Int) = QuizAnswerDto(
        id = id,
        quizId = 0,
        questionId = 0,
        dayId = 0,
        userId = 0,
        answer = "",
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
    )
}