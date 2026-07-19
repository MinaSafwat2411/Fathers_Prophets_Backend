package com.fathersprophets.backend.services.person.mcq.personmcq

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personmcq.CreatePersonMcqRequest
import com.fathersprophets.backend.models.personmcq.PersonMcqResponse
import com.fathersprophets.backend.models.personmcq.UpdatePersonMcqRequest

interface IPersonMcqService {
    fun getAllPersonMcqs(lang: String): ApiResponse<List<PersonMcqResponse>>
    fun getPersonMcqById(id: Int?, lang: String): ApiResponse<PersonMcqResponse>
    fun createPersonMcq(request: CreatePersonMcqRequest, lang: String): ApiResponse<PersonMcqResponse>
    fun updatePersonMcq(id: Int?, request: UpdatePersonMcqRequest, lang: String): ApiResponse<PersonMcqResponse>
    fun deletePersonMcq(id: Int?, lang: String): ApiResponse<Nothing>
}