package com.fathersprophets.backend.database.repository.personstory

import com.fathersprophets.backend.database.dao.person.story.PersonStoryDao
import com.fathersprophets.backend.database.repository.person.personstory.IPersonStoryRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstory.CreatePersonStoryRequest
import com.fathersprophets.backend.models.personstory.PersonStoryResponse
import com.fathersprophets.backend.models.personstory.UpdatePersonStoryRequest
import com.fathersprophets.backend.utils.Localization

class PersonStoryRepository(
    private val personStoryDao: PersonStoryDao
) : IPersonStoryRepository {

    override fun getAllStories(lang: String): ApiResponse<List<PersonStoryResponse>> {
        val stories = personStoryDao.findAll()
        return ApiResponse(
            success = true,
            data = stories.map { it.convertToPersonStoryResponse() },
            message = Localization.get("person_stories_retrieved_successfully", lang)
        )
    }

    override fun getStoryById(id: Int, lang: String): ApiResponse<PersonStoryResponse> {
        val story = personStoryDao.findById(id)
        return ApiResponse(
            success = true,
            data = story?.convertToPersonStoryResponse(),
            message = Localization.get("person_story_retrieved_successfully", lang)
        )
    }

    override fun getStoriesByPersonId(personId: Int, lang: String): ApiResponse<List<PersonStoryResponse>> {
        val stories = personStoryDao.findByPersonId(personId)
        return ApiResponse(
            success = true,
            data = stories.map { it.convertToPersonStoryResponse() },
            message = Localization.get("person_stories_retrieved_successfully", lang)
        )
    }

    override fun addStory(request: CreatePersonStoryRequest, lang: String): ApiResponse<Int> {
        val id = personStoryDao.create(request.convertToDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("person_story_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("person_story_created_successfully", lang)
        )
    }

    override fun updateStory(id: Int, request: UpdatePersonStoryRequest, lang: String): ApiResponse<Nothing> {

        val updated = personStoryDao.update(request.convertToPersonStoryDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("person_story_update_failed", lang))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_story_updated_successfully", lang)
        )
    }

    override fun deleteStory(id: Int, lang: String): ApiResponse<Nothing> {
        val deleted = personStoryDao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("person_story_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_story_deleted_successfully", lang)
        )
    }
}
