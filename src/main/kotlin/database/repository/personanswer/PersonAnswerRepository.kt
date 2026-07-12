package com.fathersprophets.backend.database.repository.personanswer

import com.fathersprophets.backend.database.dao.activity.complete.PersonAnswerDao
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.PersonAnswerDto
import com.fathersprophets.backend.models.personanswer.CreatePersonAnswerRequest
import com.fathersprophets.backend.models.personanswer.PersonAnswerResponse
import com.fathersprophets.backend.models.personanswer.UpdateAnswerStatusRequest
import com.fathersprophets.backend.models.personanswer.UpdatePersonAnswerRequest
import com.fathersprophets.backend.utils.Localization

class PersonAnswerRepository(
    private val personAnswerDao: PersonAnswerDao,
) : IPersonAnswerRepository {

    override fun getAllPersonAnswers(lang: String): ApiResponse<List<PersonAnswerResponse>> {
        val answers = personAnswerDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToPersonAnswerResponse() },
            message = Localization.get("person_answers_retrieved_successfully", lang)
        )
    }

    override fun getPersonAnswerById(id: Int, lang: String): ApiResponse<PersonAnswerResponse> {
        val answer = personAnswerDao.findById(id)
        return ApiResponse(
            success = true,
            data = answer?.convertToPersonAnswerResponse(),
            message = Localization.get("person_answer_retrieved_successfully", lang)
        )
    }

    override fun getPersonAnswersByQuestionId(questionId: Int, lang: String): ApiResponse<List<PersonAnswerResponse>> {
        val answers = personAnswerDao.findByQuestionId(questionId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToPersonAnswerResponse() },
            message = Localization.get("person_answers_retrieved_successfully", lang)
        )
    }

    override fun getPersonAnswersByUserId(userId: Int, lang: String): ApiResponse<List<PersonAnswerResponse>> {
        val answers = personAnswerDao.findByUserId(userId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToPersonAnswerResponse() },
            message = Localization.get("person_answers_retrieved_successfully", lang)
        )
    }

    override fun createPersonAnswer(request: CreatePersonAnswerRequest, lang: String): ApiResponse<PersonAnswerResponse> {
        val existing = personAnswerDao.findByQuestionIdAndUserId(request.questionId, request.userId)
        if (existing != null) throw IllegalStateException(Localization.get("person_answer_already_exists", lang))

        val id = personAnswerDao.create(request.convertToPersonAnswerDto())

        val created = personAnswerDao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToPersonAnswerResponse(),
            message = Localization.get("person_answer_created_successfully", lang)
        )
    }

    override fun updatePersonAnswer(id: Int, request: UpdatePersonAnswerRequest, lang: String): ApiResponse<PersonAnswerResponse> {
        personAnswerDao.update(request.convertToPersonAnswerDto(id))
        val updated = personAnswerDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToPersonAnswerResponse(),
            message = Localization.get("person_answer_updated_successfully", lang)
        )
    }

    override fun updatePersonAnswerStatus(id: Int, request: UpdateAnswerStatusRequest, lang: String): ApiResponse<PersonAnswerResponse> {
        personAnswerDao.updateStatus(statusToDto(id, AnswerStatus.valueOf(request.status)))
        val updated = personAnswerDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToPersonAnswerResponse(),
            message = Localization.get("person_answer_status_updated_successfully", lang)
        )
    }

    override fun deletePersonAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        personAnswerDao.delete(id)
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_answer_deleted_successfully", lang)
        )
    }

    private fun statusToDto(id: Int, status: AnswerStatus) = PersonAnswerDto(
        id = id,
        answer = "",
        questionId = 0,
        userId = 0,
        status = status
    )
}