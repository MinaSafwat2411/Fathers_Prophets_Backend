package com.fathersprophets.backend.database.repository.person.personofday

import com.fathersprophets.backend.database.dao.person.personofday.PersonOfDayDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personofday.CreatePersonOfDayRequest
import com.fathersprophets.backend.models.personofday.PersonOfDayResponse
import com.fathersprophets.backend.models.personofday.UpdatePersonOfDayRequest
import com.fathersprophets.backend.utils.Localization

class PersonOfDayRepository(
    private val personOfDayDao: PersonOfDayDao
) : IPersonOfDayRepository {

    override fun getAllPersonsOfDay(lang: String): ApiResponse<List<PersonOfDayResponse>> {
        val items = personOfDayDao.getAllPersonsOfDay()
        return ApiResponse(
            success = true,
            data = items.map { it.convertToResponse() },
            message = Localization.get("persons_of_day_retrieved_successfully", lang)
        )
    }

    override fun getPersonOfDayById(id: Int, lang: String): ApiResponse<PersonOfDayResponse> {
        val item = personOfDayDao.getPersonOfDayById(id)
        return ApiResponse(
            success = true,
            data = item?.convertToResponse(),
            message = Localization.get("person_of_day_retrieved_successfully", lang)
        )
    }

    override fun getPersonOfDayByDate(lang: String): ApiResponse<PersonOfDayResponse> {
        val item = personOfDayDao.getPersonOfDayByDate()
        return ApiResponse(
            success = true,
            data = item?.convertToResponse(),
            message = Localization.get("person_of_day_retrieved_successfully", lang)
        )
    }

    override fun addPersonOfDay(request: CreatePersonOfDayRequest, lang: String): ApiResponse<Int> {

        val id = personOfDayDao.addPersonOfDay(request.convertToDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("person_of_day_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("person_of_day_created_successfully", lang)
        )
    }

    override fun updatePersonOfDay(id: Int, request: UpdatePersonOfDayRequest, lang: String): ApiResponse<Nothing> {

        val updated = personOfDayDao.updatePersonOfDay(request.convertToPersonOfDayDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("person_of_day_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_of_day_updated_successfully", lang)
        )
    }

    override fun deletePersonOfDay(id: Int, lang: String): ApiResponse<Nothing> {
        val  deleted = personOfDayDao.deletePersonOfDay(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("person_of_day_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_of_day_deleted_successfully", lang)
        )
    }
}