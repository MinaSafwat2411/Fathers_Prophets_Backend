package com.fathersprophets.backend.database.repository.personmcq

import com.fathersprophets.backend.database.dao.PersonMcqDao
import com.fathersprophets.backend.database.tables.McqCorrectAnswer
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.PersonMcqDto
import com.fathersprophets.backend.models.personmcq.CreatePersonMcqRequest
import com.fathersprophets.backend.models.personmcq.PersonMcqResponse
import com.fathersprophets.backend.models.personmcq.UpdatePersonMcqRequest
import com.fathersprophets.backend.utils.Localization

class PersonMcqRepository(
    private val personMcqDao: PersonMcqDao
) : IPersonMcqRepository {

    override fun getAllPersonMcqs(lang: String): ApiResponse<List<PersonMcqResponse>> {
        val mcqs = personMcqDao.findAll()
        return ApiResponse(
            success = true,
            data = mcqs.map { it.convertToPersonMcqResponse() },
            message = Localization.get("person_mcqs_retrieved_successfully", lang)
        )
    }

    override fun getPersonMcqById(id: Int, lang: String): ApiResponse<PersonMcqResponse> {
        val mcq = personMcqDao.findById(id)
        return ApiResponse(
            success = true,
            data = mcq?.convertToPersonMcqResponse(),
            message = Localization.get("person_mcq_retrieved_successfully", lang)
        )
    }

    override fun getPersonMcqsByQuestionId(questionId: Int, lang: String): ApiResponse<List<PersonMcqResponse>> {
        val mcqs = personMcqDao.findByQuestionId(questionId)
        return ApiResponse(
            success = true,
            data = mcqs.map { it.convertToPersonMcqResponse() },
            message = Localization.get("person_mcqs_retrieved_successfully", lang)
        )
    }

    override fun createPersonMcq(request: CreatePersonMcqRequest, lang: String): ApiResponse<PersonMcqResponse> {
        val id = personMcqDao.create(request.convertToPersonMcqDto())
        val created = personMcqDao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToPersonMcqResponse(),
            message = Localization.get("person_mcq_created_successfully", lang)
        )
    }

    override fun updatePersonMcq(id: Int, request: UpdatePersonMcqRequest, lang: String): ApiResponse<PersonMcqResponse> {
        personMcqDao.update(request.convertToPersonMcqDto(id))
        val updated = personMcqDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToPersonMcqResponse(),
            message = Localization.get("person_mcq_updated_successfully", lang)
        )
    }

    override fun deletePersonMcq(id: Int, lang: String): ApiResponse<Nothing> {
        personMcqDao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_mcq_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = PersonMcqDto(
        id = id,
        questionId = 0,
        question = "",
        first = "",
        second = "",
        third = "",
        fourth = "",
        correctAnswer = McqCorrectAnswer.`1`
    )
}