package com.fathersprophets.backend.services.person

import com.fathersprophets.backend.database.repository.person.IPersonRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.person.PersonResponse
import com.fathersprophets.backend.models.person.UpdatePersonRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonService(
    private val personRepository: IPersonRepository
) : IPersonService {
    override fun getAllPersons(lang: String): ApiResponse<List<PersonResponse>> {
        return personRepository.getAllPersons(lang)
    }

    override fun getPersonById(
        personId: Int?,
        lang: String
    ): ApiResponse<PersonResponse> {
        if (personId == null) throw IllegalArgumentException(Localization.get("person_id_required", lang))
        return personRepository.getPersonById(personId, lang)
    }

    override fun addPerson(
        person: UpdatePersonRequest,
        lang: String
    ): ApiResponse<PersonResponse> {
        validateRequired(
            person.name to "name",
            person.type to "type",
            lang = lang
        )
        return personRepository.addPerson(person, lang)
    }

    override fun updatePerson(
        personId: Int?,
        update: UpdatePersonRequest,
        lang: String
    ): ApiResponse<PersonResponse> {
        if (personId == null) throw IllegalArgumentException(Localization.get("person_id_required", lang))
        return personRepository.updatePerson(personId, update, lang)
    }

    override fun deletePerson(
        personId: Int?,
        lang: String
    ): ApiResponse<Nothing> {
        if (personId == null) throw IllegalArgumentException(Localization.get("person_id_required", lang))
        return personRepository.deletePerson(personId, lang)
    }
}
