package com.fathersprophets.backend.services.person.personofday

import com.fathersprophets.backend.database.repository.person.personofday.IPersonOfDayRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personofday.CreatePersonOfDayRequest
import com.fathersprophets.backend.models.personofday.PersonOfDayResponse
import com.fathersprophets.backend.models.personofday.UpdatePersonOfDayRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonOfDayService(
    private val personOfDayRepository: IPersonOfDayRepository
) : IPersonOfDayService {

    override fun getAllPersonsOfDay(lang: String): ApiResponse<List<PersonOfDayResponse>> {
        return personOfDayRepository.getAllPersonsOfDay(lang)
    }

    override fun getPersonOfDayById(id: Int?, lang: String): ApiResponse<PersonOfDayResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_of_day_id_required", lang))
        return personOfDayRepository.getPersonOfDayById(id, lang)
    }

    override fun getPersonOfDayByDate(lang: String): ApiResponse<PersonOfDayResponse> {
        return personOfDayRepository.getPersonOfDayByDate(lang)
    }

    override fun addPersonOfDay(request: CreatePersonOfDayRequest, lang: String): ApiResponse<Int> {
        validateRequired(
            request.personId to "personId",
            request.message to "message",
            request.verse to "verse",
            request.date to "date",
            lang = lang
        )
        return personOfDayRepository.addPersonOfDay(request, lang)
    }

    override fun updatePersonOfDay(id: Int?, request: UpdatePersonOfDayRequest, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_of_day_id_required", lang))
        return personOfDayRepository.updatePersonOfDay(id, request, lang)
    }

    override fun deletePersonOfDay(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_of_day_id_required", lang))
        return personOfDayRepository.deletePersonOfDay(id, lang)
    }
}