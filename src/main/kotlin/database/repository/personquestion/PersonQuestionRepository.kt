package com.fathersprophets.backend.database.repository.personquestion

import com.fathersprophets.backend.database.dao.PersonQuestionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.PersonQuestionDto
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
        val question = personQuestionDao.findById(idToDto(id))
        return ApiResponse(
            success = true,
            data = question?.convertToPersonQuestionResponse(),
            message = Localization.get("person_question_retrieved_successfully", lang)
        )
    }

    override fun getPersonQuestionsByPersonId(personId: Int, lang: String): ApiResponse<List<PersonQuestionResponse>> {
        val questions = personQuestionDao.findByPersonId(personIdToDto(personId))
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToPersonQuestionResponse() },
            message = Localization.get("person_questions_retrieved_successfully", lang)
        )
    }

    override fun createPersonQuestion(request: CreateQuestionRequest, lang: String): ApiResponse<PersonQuestionResponse> {
        val id = personQuestionDao.create(request.convertToPersonQuestionDto())
        val created = personQuestionDao.findById(idToDto(id))
        return ApiResponse(
            success = true,
            data = created?.convertToPersonQuestionResponse(),
            message = Localization.get("person_question_created_successfully", lang)
        )
    }

    override fun updatePersonQuestion(id: Int, request: UpdateQuestionRequest, lang: String): ApiResponse<PersonQuestionResponse> {
        personQuestionDao.update(request.convertToPersonQuestionDto(id))
        val updated = personQuestionDao.findById(idToDto(id))
        return ApiResponse(
            success = true,
            data = updated?.convertToPersonQuestionResponse(),
            message = Localization.get("person_question_updated_successfully", lang)
        )
    }

    override fun deletePersonQuestion(id: Int, lang: String): ApiResponse<Nothing> {
        personQuestionDao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_question_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = PersonQuestionDto(
        id = id,
        question = "",
        personId = 0,
        type = com.fathersprophets.backend.database.tables.QuestionType.mcq
    )

    private fun personIdToDto(personId: Int) = PersonQuestionDto(
        id = 0,
        question = "",
        personId = personId,
        type = com.fathersprophets.backend.database.tables.QuestionType.mcq
    )
}