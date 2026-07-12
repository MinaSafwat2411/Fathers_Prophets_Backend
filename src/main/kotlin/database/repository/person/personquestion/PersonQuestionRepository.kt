package com.fathersprophets.backend.database.repository.person.personquestion

import com.fathersprophets.backend.database.dao.person.complete.PersonQuestionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personquestion.CreateQuestionRequest
import com.fathersprophets.backend.models.personquestion.PersonQuestionResponse
import com.fathersprophets.backend.models.personquestion.UpdateQuestionRequest
import com.fathersprophets.backend.utils.Localization

class PersonQuestionRepository(
    private val personQuestionDao: PersonQuestionDao
) : IPersonQuestionRepository {

    override fun getAllPersonQuestions(lang: String): ApiResponse<List<PersonQuestionResponse>> {
        val questions = personQuestionDao.findAll()
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToPersonQuestionResponse() },
            message = Localization.get("person_questions_retrieved_successfully", lang)
        )
    }

    override fun getPersonQuestionById(id: Int, lang: String): ApiResponse<PersonQuestionResponse> {
        val question = personQuestionDao.findById(id)
        return ApiResponse(
            success = true,
            data = question?.convertToPersonQuestionResponse(),
            message = Localization.get("person_question_retrieved_successfully", lang)
        )
    }

    override fun getPersonQuestionsByPersonId(personId: Int, lang: String): ApiResponse<List<PersonQuestionResponse>> {
        val questions = personQuestionDao.findByPersonId(personId)
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToPersonQuestionResponse() },
            message = Localization.get("person_questions_retrieved_successfully", lang)
        )
    }

    override fun createPersonQuestion(request: CreateQuestionRequest, lang: String): ApiResponse<Int> {
        val id = personQuestionDao.create(request.convertToPersonQuestionDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("person_question_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("person_question_created_successfully", lang)
        )
    }

    override fun updatePersonQuestion(id: Int, request: UpdateQuestionRequest, lang: String): ApiResponse<Nothing> {
        val update = personQuestionDao.update(request.convertToPersonQuestionDto(id))

        if (!update) throw IllegalArgumentException(Localization.get("person_question_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_question_updated_successfully", lang)
        )
    }

    override fun deletePersonQuestion(id: Int, lang: String): ApiResponse<Nothing> {

        val  deleted = personQuestionDao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("person_question_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_question_deleted_successfully", lang)
        )
    }
}