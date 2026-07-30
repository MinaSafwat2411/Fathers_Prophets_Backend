package com.fathersprophets.backend.database.repository.person.personmcq.personmcqanswer

import com.fathersprophets.backend.database.dao.PersonMcqAnswerDao
import com.fathersprophets.backend.database.dao.PersonMcqDao
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.database.tables.person.mcq.McqCorrectAnswer
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.PersonMcqDto
import com.fathersprophets.backend.models.personmcqanswer.CreatePersonMcqAnswerRequest
import com.fathersprophets.backend.models.personmcqanswer.PersonMcqAnswerResponse
import com.fathersprophets.backend.utils.Localization

class PersonMcqAnswerRepository(
    private val personMcqAnswerDao: PersonMcqAnswerDao,
    private val personMcqDao: PersonMcqDao
) : IPersonMcqAnswerRepository {

    override fun getAllPersonMcqAnswers(lang: String): ApiResponse<List<PersonMcqAnswerResponse>> {
        val answers = personMcqAnswerDao.findAll()
        val questions = personMcqDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map {
                it.convertToPersonMcqAnswerResponse()
                    .copy(correctAnswer = questions.find { q -> q.id == it.questionId }?.correctAnswer?.name ?: "")
            },
            message = Localization.get("person_mcq_answers_retrieved_successfully", lang)
        )
    }

    override fun getPersonMcqAnswersByUserIdAndQuestionId(
        userId: Int,
        questionId: Int,
        lang: String
    ): ApiResponse<List<PersonMcqAnswerResponse>> {
        val answers = personMcqAnswerDao.findByUserIdAndQuestionId(userId, questionId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToPersonMcqAnswerResponse() },
            message = Localization.get("person_mcq_answers_retrieved_successfully", lang)
        )
    }

    override fun createPersonMcqAnswer(
        request: CreatePersonMcqAnswerRequest,
        lang: String
    ): ApiResponse<PersonMcqAnswerResponse> {

        val mcq = personMcqDao.findById(request.questionId)
            ?: throw IllegalArgumentException(Localization.get("person_mcq_not_found", lang))

        val correctAnswer = getCorrectAnswer(mcq)

        val status = gradeAnswer(request.answer, correctAnswer)

        val create = personMcqAnswerDao.create(request.convertToPersonMcqAnswerDto().copy(status = status))
            ?: throw IllegalArgumentException(Localization.get("person_mcq_answer_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = create.convertToPersonMcqAnswerResponse(),
            message = Localization.get("person_mcq_answer_created_successfully", lang)
        )
    }

    override fun deletePersonMcqAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        personMcqAnswerDao.delete(id)
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_mcq_answer_deleted_successfully", lang)
        )
    }

    private fun gradeAnswer(answer: String, correctAnswer: String): AnswerStatus {
        return if (answer == correctAnswer) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE
    }

    private fun getCorrectAnswer(mcq: PersonMcqDto): String {
        return when (mcq.correctAnswer) {
            McqCorrectAnswer.first -> {
                mcq.first
            }

            McqCorrectAnswer.second -> {
                mcq.second
            }

            McqCorrectAnswer.third -> {
                mcq.third
            }

            McqCorrectAnswer.fourth -> {
                mcq.fourth
            }
        }
    }
}