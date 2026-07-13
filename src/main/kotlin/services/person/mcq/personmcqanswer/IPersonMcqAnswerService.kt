package com.fathersprophets.backend.services.person.mcq.personmcqanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personmcqanswer.CreatePersonMcqAnswerRequest
import com.fathersprophets.backend.models.personmcqanswer.PersonMcqAnswerResponse
import com.fathersprophets.backend.models.personmcqanswer.UpdateMcqAnswerStatusRequest
import com.fathersprophets.backend.models.personmcqanswer.UpdatePersonMcqAnswerRequest

interface IPersonMcqAnswerService {
    fun getAllPersonMcqAnswers(lang: String): ApiResponse<List<PersonMcqAnswerResponse>>
    fun getPersonMcqAnswerById(id: Int?, lang: String): ApiResponse<PersonMcqAnswerResponse>
    fun getPersonMcqAnswersByQuestionId(questionId: Int?, lang: String): ApiResponse<List<PersonMcqAnswerResponse>>
    fun getPersonMcqAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<PersonMcqAnswerResponse>>
    fun createPersonMcqAnswer(request: CreatePersonMcqAnswerRequest, lang: String): ApiResponse<Int>
    fun updatePersonMcqAnswer(id: Int?, request: UpdatePersonMcqAnswerRequest, lang: String): ApiResponse<Nothing>
    fun updatePersonMcqAnswerStatus(id: Int?, request: UpdateMcqAnswerStatusRequest, lang: String): ApiResponse<Nothing>
    fun deletePersonMcqAnswer(id: Int?, lang: String): ApiResponse<Nothing>
}