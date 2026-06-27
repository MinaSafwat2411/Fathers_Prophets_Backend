package com.fathersprophets.backend.database.repository.personofday

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personofday.PersonOfDayRequest
import com.fathersprophets.backend.models.personofday.PersonOfDayResponse

interface IPersonOfDayRepository {
    fun getAllPersonsOfDay(lang: String): ApiResponse<List<PersonOfDayResponse>>
    fun getPersonOfDayById(id: Int, lang: String): ApiResponse<PersonOfDayResponse>
    fun getPersonOfDayByDate(date: String, lang: String): ApiResponse<PersonOfDayResponse>
    fun addPersonOfDay(request: PersonOfDayRequest, lang: String): ApiResponse<PersonOfDayResponse>
    fun updatePersonOfDay(id: Int, request: PersonOfDayRequest, lang: String): ApiResponse<PersonOfDayResponse>
    fun deletePersonOfDay(id: Int, lang: String): ApiResponse<Nothing>
}