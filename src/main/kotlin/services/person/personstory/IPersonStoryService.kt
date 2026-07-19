package com.fathersprophets.backend.services.person.personstory

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstory.CreatePersonStoryRequest
import com.fathersprophets.backend.models.personstory.PersonStoryResponse
import com.fathersprophets.backend.models.personstory.UpdatePersonStoryRequest

interface IPersonStoryService {
    fun getAllStories(lang: String): ApiResponse<List<PersonStoryResponse>>
    fun getStoryById(id: Int?, lang: String): ApiResponse<PersonStoryResponse>
    fun getStoriesByPersonId(personId: Int?, lang: String): ApiResponse<List<PersonStoryResponse>>
    fun addStory(request: CreatePersonStoryRequest, lang: String): ApiResponse<PersonStoryResponse>
    fun updateStory(id: Int?, request: UpdatePersonStoryRequest, lang: String): ApiResponse<PersonStoryResponse>
    fun deleteStory(id: Int?, lang: String): ApiResponse<Nothing>
}
