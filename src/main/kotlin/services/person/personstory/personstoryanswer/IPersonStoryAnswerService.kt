package com.fathersprophets.backend.services.person.personstory.personstoryanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstoryanswer.CreatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.PersonStoryAnswerResponse
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerStatusRequest

interface IPersonStoryAnswerService {
    fun getAllPersonStoryAnswers(lang: String): ApiResponse<List<PersonStoryAnswerResponse>>
    fun getPersonStoryAnswerById(id: Int?, lang: String): ApiResponse<PersonStoryAnswerResponse>
    fun getPersonStoryAnswersByStoryId(storyId: Int?, lang: String): ApiResponse<List<PersonStoryAnswerResponse>>
    fun getPersonStoryAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<PersonStoryAnswerResponse>>
    fun getPersonStoryAnswersByQuestionId(questionId: Int?, lang: String): ApiResponse<List<PersonStoryAnswerResponse>>
    fun createPersonStoryAnswer(request: CreatePersonStoryAnswerRequest, lang: String): ApiResponse<Int>
    fun updatePersonStoryAnswer(id: Int?, request: UpdatePersonStoryAnswerRequest, lang: String): ApiResponse<Nothing>
    fun updatePersonStoryAnswerStatus(id: Int?, request: UpdatePersonStoryAnswerStatusRequest, lang: String): ApiResponse<Nothing>
    fun deletePersonStoryAnswer(id: Int?, lang: String): ApiResponse<Nothing>
}