package com.fathersprophets.backend.database.repository.person.personstory.personstoryquestion

import com.fathersprophets.backend.database.dao.person.story.PersonStoryQuestionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstoryquestion.CreatePersonStoryQuestionRequest
import com.fathersprophets.backend.models.personstoryquestion.PersonStoryQuestionResponse
import com.fathersprophets.backend.models.personstoryquestion.UpdatePersonStoryQuestionRequest
import com.fathersprophets.backend.utils.Localization

class PersonStoryQuestionRepository(
    private val personStoryQuestionDao: PersonStoryQuestionDao
) : IPersonStoryQuestionRepository {

    override fun getAllPersonStoryQuestions(lang: String): ApiResponse<List<PersonStoryQuestionResponse>> {
        val questions = personStoryQuestionDao.findAll()
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToPersonStoryQuestionResponse() },
            message = Localization.get("person_story_questions_retrieved_successfully", lang)
        )
    }

    override fun getPersonStoryQuestionsByStoryId(storyId: Int, lang: String): ApiResponse<List<PersonStoryQuestionResponse>> {
        val questions = personStoryQuestionDao.findByStoryId(storyId)
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToPersonStoryQuestionResponse() },
            message = Localization.get("person_story_questions_retrieved_successfully", lang)
        )
    }

    override fun createPersonStoryQuestion(request: CreatePersonStoryQuestionRequest, lang: String): ApiResponse<PersonStoryQuestionResponse> {
        val created = personStoryQuestionDao.create(request.convertToPersonStoryQuestionDto())
            ?:throw IllegalArgumentException(Localization.get("person_story_question_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = created.convertToPersonStoryQuestionResponse(),
            message = Localization.get("person_story_question_created_successfully", lang)
        )
    }

    override fun updatePersonStoryQuestion(id: Int, request: UpdatePersonStoryQuestionRequest, lang: String): ApiResponse<PersonStoryQuestionResponse> {
        val updated = personStoryQuestionDao.update(request.convertToPersonStoryQuestionDto(id))
            ?:throw IllegalArgumentException(Localization.get("person_story_question_update_failed", lang))
        return ApiResponse(
            success = true,
            data = updated.convertToPersonStoryQuestionResponse(),
            message = Localization.get("person_story_question_updated_successfully", lang)
        )
    }

    override fun deletePersonStoryQuestion(id: Int, lang: String): ApiResponse<Nothing> {
        val deleted = personStoryQuestionDao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("person_story_question_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_story_question_deleted_successfully", lang)
        )
    }
}