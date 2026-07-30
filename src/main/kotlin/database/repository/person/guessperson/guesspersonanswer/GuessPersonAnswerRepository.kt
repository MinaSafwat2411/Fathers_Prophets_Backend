package com.fathersprophets.backend.database.repository.person.guessperson.guesspersonanswer

import com.fathersprophets.backend.database.dao.GuessPersonAnswerDao
import com.fathersprophets.backend.database.dao.GuessPersonQuestionDao
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.database.tables.person.mcq.McqCorrectAnswer
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.GuessPersonQuestionDto
import com.fathersprophets.backend.models.guessperson.GuessPersonChoice
import com.fathersprophets.backend.models.guesspersonanswer.CreateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.GuessPersonAnswerResponse
import com.fathersprophets.backend.utils.Localization

class GuessPersonAnswerRepository(
    private val answerDao: GuessPersonAnswerDao,
    private val questionDao: GuessPersonQuestionDao
) : IGuessPersonAnswerRepository {

    override fun getAllAnswers(lang: String): ApiResponse<List<GuessPersonAnswerResponse>> {
        val answers = answerDao.findAll()
        val questions = questionDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { answerDto ->
                answerDto.convertToResponse()
                    .copy(correctAnswer = questions.find { it.id == answerDto.questionId }?.let { getCorrectAnswer(it) })
            },
            message = Localization.get("guess_person_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByUserIdAndQuestionId(
        userId: Int,
        questionId: Int,
        lang: String
    ): ApiResponse<List<GuessPersonAnswerResponse>> {
        val answers = answerDao.findByUserIdAndQuestionId(userId, questionId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("guess_person_answers_retrieved_successfully", lang)
        )
    }

    override fun createAnswer(
        request: CreateGuessPersonAnswerRequest,
        lang: String
    ): ApiResponse<GuessPersonAnswerResponse> {

        val questionDao = questionDao.findById(request.questionId)
            ?: throw IllegalArgumentException(Localization.get("guess_person_question_not_found", lang))

        val status = gradeAnswer(request.personId, questionDao.correctPersonId)

        val create = answerDao.create(request.convertToDto().copy(status = status))
            ?: throw IllegalArgumentException(Localization.get("guess_person_answer_creation_failed", lang))


        return ApiResponse(
            success = true,
            data = create.convertToResponse(),
            message = Localization.get("guess_person_answer_created_successfully", lang)
        )
    }

    override fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        answerDao.delete(id)
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("guess_person_answer_deleted_successfully", lang)
        )
    }

    private fun gradeAnswer(answer: Int, correctAnswer: Int) =
        if (answer == correctAnswer) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE

    private fun getCorrectAnswer(question: GuessPersonQuestionDto): GuessPersonChoice =
        when (question.correctAnswer) {
            McqCorrectAnswer.first -> question.first
            McqCorrectAnswer.second -> question.second
            McqCorrectAnswer.third -> question.third
            McqCorrectAnswer.fourth -> question.fourth
        }

}