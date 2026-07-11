package com.fathersprophets.backend.database.repository.personofday

import com.fathersprophets.backend.database.dao.person.PersonOfDayDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.PersonOfDayDto
import com.fathersprophets.backend.models.personofday.CreatePersonOfDayRequest
import com.fathersprophets.backend.models.personofday.PersonOfDayResponse
import com.fathersprophets.backend.models.personofday.UpdatePersonOfDayRequest
import com.fathersprophets.backend.utils.Localization
import java.time.LocalDate

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

    override fun getPersonOfDayByDate(date: String, lang: String): ApiResponse<PersonOfDayResponse> {
        val localDate = LocalDate.parse(date)
        val item = personOfDayDao.getPersonOfDayByDate(localDate)
        return ApiResponse(
            success = true,
            data = item?.convertToResponse(),
            message = Localization.get("person_of_day_retrieved_successfully", lang)
        )
    }

    override fun addPersonOfDay(request: CreatePersonOfDayRequest, lang: String): ApiResponse<PersonOfDayResponse> {
        val dto = PersonOfDayDto(
            id = 0,
            personId = request.personId?:0,
            message = request.message?:"",
            verse = request.verse?:"",
            date = LocalDate.parse(request.date?:"")
        )
        val id = personOfDayDao.addPersonOfDay(dto)
        val created = personOfDayDao.getPersonOfDayById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("person_of_day_created_successfully", lang)
        )
    }

    override fun updatePersonOfDay(id: Int, request: UpdatePersonOfDayRequest, lang: String): ApiResponse<PersonOfDayResponse> {
        val existing = personOfDayDao.getPersonOfDayById(id)?: return ApiResponse(
            success = false,
            data = null,
            message = Localization.get("person_of_day_not_found", lang)
        )
        val dto = PersonOfDayDto(
            id = id,
            personId = request.personId ?: existing.personId,
            message = request.message ?: existing.message,
            verse = request.verse ?: existing.verse,
            date = if (request.date != null) LocalDate.parse(request.date) else existing.date
        )
        personOfDayDao.updatePersonOfDay(dto)
        val updated = personOfDayDao.getPersonOfDayById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("person_of_day_updated_successfully", lang)
        )
    }

    override fun deletePersonOfDay(id: Int, lang: String): ApiResponse<Nothing> {
        val dto = PersonOfDayDto(id = id, personId = 0, message = "", verse = "", date = LocalDate.now())
        personOfDayDao.deletePersonOfDay(dto)
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_of_day_deleted_successfully", lang)
        )
    }
}