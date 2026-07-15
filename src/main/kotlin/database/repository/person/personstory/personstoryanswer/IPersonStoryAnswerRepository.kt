package com.fathersprophets.backend.database.repository.person.personstory.personstoryanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstoryanswer.CreatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.PersonStoryAnswerResponse
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerStatusRequest

interface IPersonStoryAnswerRepository {
    fun getAllPersonStoryAnswers(lang: String): ApiResponse<List<PersonStoryAnswerResponse>>
    fun getAllPersonStoryAnswersByUserIdAndStoryId(userId: Int, storyId: Int,lang: String): ApiResponse<List<PersonStoryAnswerResponse>>
    fun createPersonStoryAnswer(request: CreatePersonStoryAnswerRequest, lang: String): ApiResponse<PersonStoryAnswerResponse>
    fun updatePersonStoryAnswer(id: Int, request: UpdatePersonStoryAnswerRequest, lang: String): ApiResponse<PersonStoryAnswerResponse>
    fun updatePersonStoryAnswerStatus(id: Int, request: UpdatePersonStoryAnswerStatusRequest, lang: String): ApiResponse<PersonStoryAnswerResponse>
    fun deletePersonStoryAnswer(id: Int, lang: String): ApiResponse<Nothing>
}