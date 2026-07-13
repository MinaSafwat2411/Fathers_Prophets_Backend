package com.fathersprophets.backend.database.repository.person.personmcq

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personmcq.CreatePersonMcqRequest
import com.fathersprophets.backend.models.personmcq.PersonMcqResponse
import com.fathersprophets.backend.models.personmcq.UpdatePersonMcqRequest

interface IPersonMcqRepository {
    fun getAllPersonMcqs(lang: String): ApiResponse<List<PersonMcqResponse>>
    fun getPersonMcqById(id: Int, lang: String): ApiResponse<PersonMcqResponse>

    fun getByPersonId(personId: Int, lang: String): ApiResponse<List<PersonMcqResponse>>
    fun createPersonMcq(request: CreatePersonMcqRequest, lang: String): ApiResponse<Int>
    fun updatePersonMcq(id: Int, request: UpdatePersonMcqRequest, lang: String): ApiResponse<Nothing>
    fun deletePersonMcq(id: Int, lang: String): ApiResponse<Nothing>
}