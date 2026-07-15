package com.fathersprophets.backend.services.person

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.person.CreatePersonRequest
import com.fathersprophets.backend.models.person.PersonResponse
import com.fathersprophets.backend.models.person.UpdatePersonRequest

interface IPersonService {
    fun getAllPersons(lang: String): ApiResponse<List<PersonResponse>>
    fun addPerson(person: CreatePersonRequest, lang: String): ApiResponse<PersonResponse>

    fun getPersonByType(personType: String?, lang: String) : ApiResponse<List<PersonResponse>>
    fun updatePerson(personId: Int?, update: UpdatePersonRequest, lang: String): ApiResponse<PersonResponse>
    fun deletePerson(personId: Int?, lang: String): ApiResponse<Nothing>
}
