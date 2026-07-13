package com.fathersprophets.backend.services.person.personstory.personstoryquestion

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstoryquestion.CreatePersonStoryQuestionRequest
import com.fathersprophets.backend.models.personstoryquestion.PersonStoryQuestionResponse
import com.fathersprophets.backend.models.personstoryquestion.UpdatePersonStoryQuestionRequest

interface IPersonStoryQuestionService {
    fun getAllPersonStoryQuestions(lang: String): ApiResponse<List<PersonStoryQuestionResponse>>
    fun getPersonStoryQuestionById(id: Int?, lang: String): ApiResponse<PersonStoryQuestionResponse>
    fun getPersonStoryQuestionsByStoryId(storyId: Int?, lang: String): ApiResponse<List<PersonStoryQuestionResponse>>
    fun createPersonStoryQuestion(request: CreatePersonStoryQuestionRequest, lang: String): ApiResponse<Int>
    fun updatePersonStoryQuestion(id: Int?, request: UpdatePersonStoryQuestionRequest, lang: String): ApiResponse<Nothing>
    fun deletePersonStoryQuestion(id: Int?, lang: String): ApiResponse<Nothing>
}