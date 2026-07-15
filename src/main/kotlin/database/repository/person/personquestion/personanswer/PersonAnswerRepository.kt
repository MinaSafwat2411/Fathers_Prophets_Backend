package com.fathersprophets.backend.database.repository.person.personquestion.personanswer

import com.fathersprophets.backend.database.dao.person.complete.PersonAnswerDao
import com.fathersprophets.backend.database.dao.person.complete.PersonQuestionDao
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
    private val personQuestionDao: PersonQuestionDao
) : IPersonAnswerRepository {

    override fun getAllPersonAnswers(lang: String): ApiResponse<List<PersonAnswerResponse>> {
        val answers = personAnswerDao.findAll()
        val questions = personQuestionDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToPersonAnswerResponse().copy( correctAnswer = questions.first { q -> q.id == it.questionId }.correctAnswer) },
            message = Localization.get("person_answers_retrieved_successfully", lang)
        )
    }

    override fun getPersonAnswersByUserIdAndQuestionId(
        userId: Int,
        questionId: Int,
        lang: String
    ): ApiResponse<List<PersonAnswerResponse>> {
        val answers = personAnswerDao.findByQuestionIdAndUserId(userId, questionId)

        return ApiResponse(
            success = true,
            data = answers.map { it.convertToPersonAnswerResponse() },
            message = Localization.get("person_answers_retrieved_successfully", lang)
        )
    }

    override fun createPersonAnswer(request: CreatePersonAnswerRequest, lang: String): ApiResponse<PersonAnswerResponse> {

        val create = personAnswerDao.create(request.convertToPersonAnswerDto())
            ?:throw IllegalArgumentException(Localization.get("person_answer_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = create.convertToPersonAnswerResponse(),
            message = Localization.get("person_answer_created_successfully", lang)
        )
    }

    override fun updatePersonAnswer(id: Int, request: UpdatePersonAnswerRequest, lang: String): ApiResponse<PersonAnswerResponse> {
        val updated = personAnswerDao.update(request.convertToPersonAnswerDto(id))
            ?: throw IllegalArgumentException(Localization.get("person_answer_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToPersonAnswerResponse(),
            message = Localization.get("person_answer_updated_successfully", lang)
        )
    }

    override fun updatePersonAnswerStatus(id: Int, request: UpdateAnswerStatusRequest, lang: String): ApiResponse<PersonAnswerResponse> {
        val updated = personAnswerDao.updateStatus(statusToDto(id, AnswerStatus.valueOf(request.status)))
            ?: throw IllegalArgumentException(Localization.get("person_answer_status_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToPersonAnswerResponse(),
            message = Localization.get("person_answer_status_updated_successfully", lang)
        )
    }

    override fun deletePersonAnswer(id: Int, lang: String): ApiResponse<Nothing> {

        val deleted = personAnswerDao.delete(id)


        if (!deleted) throw IllegalArgumentException(Localization.get("person_answer_deletion_failed", lang))
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