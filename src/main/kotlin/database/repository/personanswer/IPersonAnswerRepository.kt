package com.fathersprophets.backend.database.repository.personanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personanswer.CreatePersonAnswerRequest
import com.fathersprophets.backend.models.personanswer.PersonAnswerResponse
import com.fathersprophets.backend.models.personanswer.UpdateAnswerStatusRequest
import com.fathersprophets.backend.models.personanswer.UpdatePersonAnswerRequest

interface IPersonAnswerRepository {
    fun getAllPersonAnswers(lang: String): ApiResponse<List<PersonAnswerResponse>>
    fun getPersonAnswerById(id: Int, lang: String): ApiResponse<PersonAnswerResponse>
    fun getPersonAnswersByQuestionId(questionId: Int, lang: String): ApiResponse<List<PersonAnswerResponse>>
    fun getPersonAnswersByUserId(userId: Int, lang: String): ApiResponse<List<PersonAnswerResponse>>
    fun createPersonAnswer(request: CreatePersonAnswerRequest, lang: String): ApiResponse<PersonAnswerResponse>
    fun updatePersonAnswer(id: Int, request: UpdatePersonAnswerRequest, lang: String): ApiResponse<PersonAnswerResponse>
    fun updatePersonAnswerStatus(id: Int, request: UpdateAnswerStatusRequest, lang: String): ApiResponse<PersonAnswerResponse>
    fun deletePersonAnswer(id: Int, lang: String): ApiResponse<Nothing>
}