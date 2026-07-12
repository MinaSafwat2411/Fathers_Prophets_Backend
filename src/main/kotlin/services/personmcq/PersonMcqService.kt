package com.fathersprophets.backend.services.personmcq

import com.fathersprophets.backend.database.repository.person.personmcq.IPersonMcqRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personmcq.CreatePersonMcqRequest
import com.fathersprophets.backend.models.personmcq.PersonMcqResponse
import com.fathersprophets.backend.models.personmcq.UpdatePersonMcqRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonMcqService(
    private val personMcqRepository: IPersonMcqRepository
) : IPersonMcqService {

    override fun getAllPersonMcqs(lang: String): ApiResponse<List<PersonMcqResponse>> {
        return personMcqRepository.getAllPersonMcqs(lang)
    }

    override fun getPersonMcqById(id: Int?, lang: String): ApiResponse<PersonMcqResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_mcq_id_required", lang))
        return personMcqRepository.getPersonMcqById(id, lang)
    }

    override fun createPersonMcq(request: CreatePersonMcqRequest, lang: String): ApiResponse<PersonMcqResponse> {
        validateRequired(
            request.question to "question",
            request.first to "first",
            request.second to "second",
            request.third to "third",
            request.fourth to "fourth",
            request.correctAnswer to "correctAnswer",
            lang = lang
        )
        return personMcqRepository.createPersonMcq(request, lang)
    }

    override fun updatePersonMcq(id: Int?, request: UpdatePersonMcqRequest, lang: String): ApiResponse<PersonMcqResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_mcq_id_required", lang))
        validateRequired(
            request.question to "question",
            request.first to "first",
            request.second to "second",
            request.third to "third",
            request.fourth to "fourth",
            request.correctAnswer to "correctAnswer",
            lang = lang
        )
        return personMcqRepository.updatePersonMcq(id, request, lang)
    }

    override fun deletePersonMcq(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_mcq_id_required", lang))
        return personMcqRepository.deletePersonMcq(id, lang)
    }
}