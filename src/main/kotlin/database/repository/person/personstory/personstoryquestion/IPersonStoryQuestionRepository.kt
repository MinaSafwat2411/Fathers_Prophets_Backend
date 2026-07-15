package com.fathersprophets.backend.database.repository.person.personstory.personstoryquestion

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstoryquestion.CreatePersonStoryQuestionRequest
import com.fathersprophets.backend.models.personstoryquestion.PersonStoryQuestionResponse
import com.fathersprophets.backend.models.personstoryquestion.UpdatePersonStoryQuestionRequest

interface IPersonStoryQuestionRepository {
    fun getAllPersonStoryQuestions(lang: String): ApiResponse<List<PersonStoryQuestionResponse>>
    fun getPersonStoryQuestionsByStoryId(storyId: Int, lang: String): ApiResponse<List<PersonStoryQuestionResponse>>
    fun createPersonStoryQuestion(request: CreatePersonStoryQuestionRequest, lang: String): ApiResponse<PersonStoryQuestionResponse>
    fun updatePersonStoryQuestion(id: Int, request: UpdatePersonStoryQuestionRequest, lang: String): ApiResponse<PersonStoryQuestionResponse>
    fun deletePersonStoryQuestion(id: Int, lang: String): ApiResponse<Nothing>
}