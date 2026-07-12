package com.fathersprophets.backend.database.repository.person

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.person.CreatePersonRequest
import com.fathersprophets.backend.models.person.PersonResponse
import com.fathersprophets.backend.models.person.UpdatePersonRequest

interface IPersonRepository {
    fun getAllPersons(lang: String): ApiResponse<List<PersonResponse>>
    fun getPersonById(personId: Int, lang: String): ApiResponse<PersonResponse>
    fun addPerson(person: CreatePersonRequest, lang: String): ApiResponse<Int>
    fun updatePerson(personId: Int, update: UpdatePersonRequest, lang: String): ApiResponse<Nothing>
    fun deletePerson(personId: Int, lang: String): ApiResponse<Nothing>
}
