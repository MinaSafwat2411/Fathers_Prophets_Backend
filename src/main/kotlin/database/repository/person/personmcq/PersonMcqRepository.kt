package com.fathersprophets.backend.database.repository.person.personmcq

import com.fathersprophets.backend.database.dao.person.mcq.PersonMcqDao
import com.fathersprophets.backend.models.ApiResponse
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

    override fun createPersonMcq(request: CreatePersonMcqRequest, lang: String): ApiResponse<Int> {
        val id = personMcqDao.create(request.convertToPersonMcqDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("person_mcq_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("person_mcq_created_successfully", lang)
        )
    }

    override fun updatePersonMcq(id: Int, request: UpdatePersonMcqRequest, lang: String): ApiResponse<Nothing> {
        val updated = personMcqDao.update(request.convertToPersonMcqDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("person_mcq_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_mcq_updated_successfully", lang)
        )
    }

    override fun deletePersonMcq(id: Int, lang: String): ApiResponse<Nothing> {

        val deleted = personMcqDao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("person_mcq_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_mcq_deleted_successfully", lang)
        )
    }
}