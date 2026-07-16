package com.fathersprophets.backend.database.repository.quiz.quizanswer

import com.fathersprophets.backend.database.dao.quiz.QuizAnswerDao
import com.fathersprophets.backend.database.dao.quiz.QuizDayQuestionDao
import com.fathersprophets.backend.database.dao.users.UserProgressQuizDao
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.database.tables.person.mcq.McqCorrectAnswer
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.QuizAnswerDto
import com.fathersprophets.backend.models.dto.QuizDayQuestionDto
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
        val questions = quizDayQuestionDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse().copy(correctAnswer = getCorrectAnswer(questions.first { q -> q.id == it.questionId })) },
            message = Localization.get("quiz_answers_retrieved_successfully", lang)
        )
    }

    override fun getQuizAnswersByUserIdAndDayId(
        dayId: Int,
        userId: Int,
        lang: String
    ): ApiResponse<List<QuizAnswerResponse>> {
        val answers = dao.findByUserIdAndDayId(userId, dayId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("quiz_answers_retrieved_successfully", lang)
        )
    }

    override fun createQuizAnswer(request: CreateQuizAnswerRequest, lang: String): ApiResponse<QuizAnswerResponse> {
        val dto = buildDtoForCreate(request, lang)

        val create = dao.create(dto)
            ?: throw IllegalArgumentException(Localization.get("quiz_answer_creation_failed", lang))

        trackScore(dto)

        return ApiResponse(
            success = true,
            data = create.convertToResponse(),
            message = Localization.get("quiz_answer_created_successfully", lang)
        )
    }

    override fun createQuizAnswers(
        requests: List<CreateQuizAnswerRequest>,
        lang: String
    ): ApiResponse<List<QuizAnswerResponse>> {
        val dtos = requests.map { buildDtoForCreate(it, lang) }

        val created = dao.createMany(dtos)

        if (created.size != dtos.size) throw IllegalArgumentException(
            Localization.get(
                "quiz_answers_creation_failed",
                lang
            )
        )

        created.forEach { trackScore(it) }
        return ApiResponse(
            success = true,
            data = created.map { it.convertToResponse() },
            message = Localization.get("quiz_answers_created_successfully", lang)
        )
    }

    private fun buildDtoForCreate(request: CreateQuizAnswerRequest, lang: String): QuizAnswerDto {
        val question = quizDayQuestionDao.findById(request.questionId)
            ?: throw IllegalArgumentException(Localization.get("quiz_day_question_not_found", lang))

        val status = autoGrade(request.answer, getCorrectAnswer(question))

        return request.convertToDto().copy(status = status)
    }

    private fun getCorrectAnswer(question: QuizDayQuestionDto): String {
        return when (question.correctAnswer) {
            McqCorrectAnswer.first -> question.choice1
            McqCorrectAnswer.second -> question.choice2
            McqCorrectAnswer.third -> question.choice3 ?: ""
            McqCorrectAnswer.fourth -> question.choice4 ?: ""
        }
    }

    override fun deleteQuizAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        val previous = dao.findById(id)
        val deleted = dao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("quiz_answer_deletion_failed", lang))

        if (previous?.status == AnswerStatus.IS_TRUE) {
            userProgressQuizDao.incrementScore(previous.userId, previous.quizId, previous.dayId, -1)
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
}