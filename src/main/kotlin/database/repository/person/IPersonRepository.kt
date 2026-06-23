package com.fathersprophets.backend.database.repository.person

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.person.PersonResponse
import com.fathersprophets.backend.models.person.UpdatePersonRequest

interface IPersonRepository {
    fun getAllPersons(lang: String): ApiResponse<List<PersonResponse>>
    fun getPersonById(personId: Int, lang: String): ApiResponse<PersonResponse>
    fun addPerson(person: UpdatePersonRequest, lang: String): ApiResponse<PersonResponse>
    fun updatePerson(personId: Int, update: UpdatePersonRequest, lang: String): ApiResponse<PersonResponse>
    fun deletePerson(personId: Int, lang: String): ApiResponse<Nothing>
}
