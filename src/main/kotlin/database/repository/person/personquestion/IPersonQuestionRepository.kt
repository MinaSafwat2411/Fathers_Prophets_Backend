package com.fathersprophets.backend.database.repository.person.personquestion

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personquestion.CreateQuestionRequest
import com.fathersprophets.backend.models.personquestion.PersonQuestionResponse
import com.fathersprophets.backend.models.personquestion.UpdateQuestionRequest

interface IPersonQuestionRepository {
    fun getAllPersonQuestions(lang: String): ApiResponse<List<PersonQuestionResponse>>
    fun getPersonQuestionsByPersonId(personId: Int, lang: String): ApiResponse<List<PersonQuestionResponse>>
    fun createPersonQuestion(request: CreateQuestionRequest, lang: String): ApiResponse<PersonQuestionResponse>
    fun updatePersonQuestion(id: Int, request: UpdateQuestionRequest, lang: String): ApiResponse<PersonQuestionResponse>
    fun deletePersonQuestion(id: Int, lang: String): ApiResponse<Nothing>
}