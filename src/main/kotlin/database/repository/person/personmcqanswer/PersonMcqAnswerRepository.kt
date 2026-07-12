package com.fathersprophets.backend.database.repository.person.personmcqanswer

import com.fathersprophets.backend.database.dao.person.mcq.PersonMcqAnswerDao
import com.fathersprophets.backend.database.dao.person.mcq.PersonMcqDao
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.PersonMcqAnswerDto
import com.fathersprophets.backend.models.personmcqanswer.CreatePersonMcqAnswerRequest
import com.fathersprophets.backend.models.personmcqanswer.PersonMcqAnswerResponse
import com.fathersprophets.backend.models.personmcqanswer.UpdateMcqAnswerStatusRequest
import com.fathersprophets.backend.models.personmcqanswer.UpdatePersonMcqAnswerRequest
import com.fathersprophets.backend.utils.Localization

class PersonMcqAnswerRepository(
    private val personMcqAnswerDao: PersonMcqAnswerDao,
    private val personMcqDao: PersonMcqDao
) : IPersonMcqAnswerRepository {

    override fun getAllPersonMcqAnswers(lang: String): ApiResponse<List<PersonMcqAnswerResponse>> {
        val answers = personMcqAnswerDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToPersonMcqAnswerResponse() },
            message = Localization.get("person_mcq_answers_retrieved_successfully", lang)
        )
    }

    override fun getPersonMcqAnswerById(id: Int, lang: String): ApiResponse<PersonMcqAnswerResponse> {
        val answer = personMcqAnswerDao.findById(id)
        return ApiResponse(
            success = true,
            data = answer?.convertToPersonMcqAnswerResponse(),
            message = Localization.get("person_mcq_answer_retrieved_successfully", lang)
        )
    }

    override fun getPersonMcqAnswersByQuestionId(questionId: Int, lang: String): ApiResponse<List<PersonMcqAnswerResponse>> {
        val answers = personMcqAnswerDao.findByQuestionId(questionId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToPersonMcqAnswerResponse() },
            message = Localization.get("person_mcq_answers_retrieved_successfully", lang)
        )
    }

    override fun getPersonMcqAnswersByUserId(userId: Int, lang: String): ApiResponse<List<PersonMcqAnswerResponse>> {
        val answers = personMcqAnswerDao.findByUserId(userId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToPersonMcqAnswerResponse() },
            message = Localization.get("person_mcq_answers_retrieved_successfully", lang)
        )
    }

    override fun createPersonMcqAnswer(request: CreatePersonMcqAnswerRequest, lang: String): ApiResponse<Int> {

        val mcq = personMcqDao.findById(request.questionId)
            ?: throw IllegalArgumentException(Localization.get("person_mcq_not_found", lang))

        val status = if (request.answer == mcq.correctAnswer.name) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE

        val id = personMcqAnswerDao.create(
            PersonMcqAnswerDto(
                id = 0,
                answer = request.answer,
                questionId = request.questionId,
                userId = request.userId,
                status = status
            )
        )

        if (id == 0) throw IllegalArgumentException(Localization.get("person_mcq_answer_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("person_mcq_answer_created_successfully", lang)
        )
    }

    override fun updatePersonMcqAnswer(id: Int, request: UpdatePersonMcqAnswerRequest, lang: String): ApiResponse<Nothing> {
        val updated = personMcqAnswerDao.update(request.convertToPersonMcqAnswerDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("person_mcq_answer_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_mcq_answer_updated_successfully", lang)
        )
    }

    override fun updatePersonMcqAnswerStatus(id: Int, request: UpdateMcqAnswerStatusRequest, lang: String): ApiResponse<Nothing> {
        val updated = personMcqAnswerDao.updateStatus(statusToDto(id, AnswerStatus.valueOf(request.status)))

        if (!updated) throw IllegalArgumentException(Localization.get("person_mcq_answer_status_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_mcq_answer_status_updated_successfully", lang)
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

    private fun statusToDto(id: Int, status: AnswerStatus) = PersonMcqAnswerDto(
        id = id,
        answer = "",
        questionId = 0,
        userId = 0,
        status = status
    )
}