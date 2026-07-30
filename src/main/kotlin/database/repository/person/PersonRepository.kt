package com.fathersprophets.backend.database.repository.person

import com.fathersprophets.backend.database.dao.PersonDao
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
    ): ApiResponse<PersonResponse> {
        val created = personDao.addPerson(person.toPersonDto())
            ?: throw IllegalArgumentException(Localization.get("person_creation_failed", lang))
        return ApiResponse(
            success = true,
            data = created.convertToPersonResponse(),
            message = Localization.get("person_created_successfully", lang)
        )
    }

    override fun updatePerson(
        personId: Int,
        update: UpdatePersonRequest,
        lang: String
    ): ApiResponse<PersonResponse> {

        val updated = personDao.updatePerson(update.toPersonDto(personId))
            ?: throw IllegalArgumentException(Localization.get("person_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToPersonResponse(),
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
