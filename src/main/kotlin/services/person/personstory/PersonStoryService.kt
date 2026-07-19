package com.fathersprophets.backend.services.personstory

import com.fathersprophets.backend.database.repository.person.personstory.IPersonStoryRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstory.CreatePersonStoryRequest
import com.fathersprophets.backend.models.personstory.PersonStoryResponse
import com.fathersprophets.backend.models.personstory.UpdatePersonStoryRequest
import com.fathersprophets.backend.services.person.personstory.IPersonStoryService
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonStoryService(
    private val personStoryRepository: IPersonStoryRepository
) : IPersonStoryService {

    override fun getAllStories(lang: String): ApiResponse<List<PersonStoryResponse>> {
        return personStoryRepository.getAllStories(lang)
    }

    override fun getStoryById(id: Int?, lang: String): ApiResponse<PersonStoryResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_id_required", lang))
        return personStoryRepository.getStoryById(id, lang)
    }

    override fun getStoriesByPersonId(personId: Int?, lang: String): ApiResponse<List<PersonStoryResponse>> {
        if (personId == null) throw IllegalArgumentException(Localization.get("person_id_required", lang))
        return personStoryRepository.getStoriesByPersonId(personId, lang)
    }

    override fun addStory(request: CreatePersonStoryRequest, lang: String): ApiResponse<PersonStoryResponse> {
        validateRequired(
            request.personId to "personId",
            request.title to "title",
            request.content to "content",
            lang = lang
        )
        return personStoryRepository.addStory(request, lang)
    }

    override fun updateStory(id: Int?, request: UpdatePersonStoryRequest, lang: String): ApiResponse<PersonStoryResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_id_required", lang))
        return personStoryRepository.updateStory(id, request, lang)
    }

    override fun deleteStory(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_id_required", lang))
        return personStoryRepository.deleteStory(id, lang)
    }
}
