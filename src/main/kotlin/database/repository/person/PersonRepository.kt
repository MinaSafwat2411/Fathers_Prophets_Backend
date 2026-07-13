package com.fathersprophets.backend.database.repository.person

import com.fathersprophets.backend.database.dao.person.PersonDao
import com.fathersprophets.backend.database.tables.person.PersonType
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.person.CreatePersonRequest
import com.fathersprophets.backend.models.person.PersonResponse
import com.fathersprophets.backend.models.person.UpdatePersonRequest
import com.fathersprophets.backend.utils.Localization

class PersonRepository(
    private val personDao: PersonDao,
) : IPersonRepository  {
    override fun getAllPersons(lang: String): ApiResponse<List<PersonResponse>> {
        val persons = personDao.getAllPersons()
        return ApiResponse(
            success = true,
            data = persons.map { it.convertToPersonResponse() },
            message = Localization.get("persons_retrieved_successfully", lang)
        )
    }

    override fun getPersonById(
        personId: Int,
        lang: String
    ): ApiResponse<PersonResponse> {
        val person = personDao.getPersonById(personId)
        return ApiResponse(
            success = true,
            data = person?.convertToPersonResponse(),
            message = Localization.get("person_retrieved_successfully", lang)
        )
    }

    override fun getPersonByType(
        personType: String,
        lang: String
    ): ApiResponse<List<PersonResponse>> {
        val persons = personDao.getPersonsByType(PersonType.valueOf(personType))
        return ApiResponse(
            success = true,
            data = persons.map { it.convertToPersonResponse() },
            message = Localization.get("persons_retrieved_successfully", lang)
        )
    }

    override fun addPerson(
        person: CreatePersonRequest,
        lang: String
    ): ApiResponse<Int> {
        val id = personDao.addPerson(person.toPersonDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("person_creation_failed", lang))
        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("person_created_successfully", lang)
        )
    }

    override fun updatePerson(
        personId: Int,
        update: UpdatePersonRequest,
        lang: String
    ): ApiResponse<Nothing> {

        val updated = personDao.updatePerson(update.toPersonDto(personId))

        if (!updated) throw IllegalArgumentException(Localization.get("person_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_updated_successfully", lang)
        )
    }

    override fun deletePerson(
        personId: Int,
        lang: String
    ): ApiResponse<Nothing> {

        val deleted = personDao.deletePerson(personId)

        if (!deleted) throw IllegalArgumentException(Localization.get("person_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_deleted_successfully", lang)
        )
    }
}
