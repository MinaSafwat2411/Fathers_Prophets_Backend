package com.fathersprophets.backend.database.repository.person

import com.fathersprophets.backend.database.dao.PersonDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.PersonDto
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
        val person = personDao.getPersonById(idToDto(personId))
        return ApiResponse(
            success = true,
            data = person?.convertToPersonResponse(),
            message = Localization.get("person_retrieved_successfully", lang)
        )
    }

    override fun addPerson(
        person: CreatePersonRequest,
        lang: String
    ): ApiResponse<PersonResponse> {
        val id = personDao.addPerson(person.toPersonDto())
        val createdPerson = personDao.getPersonById(idToDto(id))
        return ApiResponse(
            success = true,
            data = createdPerson?.convertToPersonResponse(),
            message = Localization.get("person_created_successfully", lang)
        )
    }

    override fun updatePerson(
        personId: Int,
        update: UpdatePersonRequest,
        lang: String
    ): ApiResponse<PersonResponse> {
        personDao.updatePerson(update.toPersonDto(personId))
        val updatedPerson = personDao.getPersonById(idToDto(personId))
        return ApiResponse(
            success = true,
            data = updatedPerson?.convertToPersonResponse(),
            message = Localization.get("person_updated_successfully", lang)
        )
    }

    override fun deletePerson(
        personId: Int,
        lang: String
    ): ApiResponse<Nothing> {
        personDao.deletePerson(idToDto(personId))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = PersonDto(
        id = id,
        name = "",
        nickname = "",
        shortStory = "",
        fullStory = "",
        image = "",
        type = com.fathersprophets.backend.database.tables.PersonType.prophets
    )
}
