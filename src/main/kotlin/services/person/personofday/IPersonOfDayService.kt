package com.fathersprophets.backend.services.person.personofday

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personofday.CreatePersonOfDayRequest
import com.fathersprophets.backend.models.personofday.PersonOfDayResponse
import com.fathersprophets.backend.models.personofday.UpdatePersonOfDayRequest

interface IPersonOfDayService {
    fun getAllPersonsOfDay(lang: String): ApiResponse<List<PersonOfDayResponse>>
    fun getPersonOfDayByDate(lang: String): ApiResponse<PersonOfDayResponse>
    fun addPersonOfDay(request: CreatePersonOfDayRequest, lang: String): ApiResponse<PersonOfDayResponse>
    fun updatePersonOfDay(id: Int?, request: UpdatePersonOfDayRequest, lang: String): ApiResponse<PersonOfDayResponse>
    fun deletePersonOfDay(id: Int?, lang: String): ApiResponse<Nothing>
}