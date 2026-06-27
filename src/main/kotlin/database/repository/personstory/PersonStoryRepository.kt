package com.fathersprophets.backend.database.repository.personstory

import com.fathersprophets.backend.database.dao.PersonStoryDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.PersonStoryDto
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

    override fun addStory(request: CreatePersonStoryRequest, lang: String): ApiResponse<PersonStoryResponse> {
        val dto = PersonStoryDto(
            id = 0,
            personId = request.personId!!,
            title = request.title!!,
            content = request.content!!,
            image = request.image,
            question = request.question!!
        )
        val id = personStoryDao.create(dto)
        val created = personStoryDao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToPersonStoryResponse(),
            message = Localization.get("person_story_created_successfully", lang)
        )
    }

    override fun updateStory(id: Int, request: UpdatePersonStoryRequest, lang: String): ApiResponse<PersonStoryResponse> {
        val existing = personStoryDao.findById(id)!!
        val dto = request.convertToPersonStoryDto(id, existing.personId)
        personStoryDao.update(dto)
        val updated = personStoryDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToPersonStoryResponse(),
            message = Localization.get("person_story_updated_successfully", lang)
        )
    }

    override fun deleteStory(id: Int, lang: String): ApiResponse<Nothing> {
        val dto = PersonStoryDto(id = id, personId = 0, title = "", content = "", image = null, question = "")
        personStoryDao.delete(dto)
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_story_deleted_successfully", lang)
        )
    }
}
