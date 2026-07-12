package com.fathersprophets.backend.database.repository.person.personstoryanswer

import com.fathersprophets.backend.database.dao.person.story.PersonStoryAnswerDao
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.PersonStoryAnswerDto
import com.fathersprophets.backend.models.personstoryanswer.CreatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.PersonStoryAnswerResponse
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerStatusRequest
import com.fathersprophets.backend.utils.Localization

class PersonStoryAnswerRepository(
    private val personStoryAnswerDao: PersonStoryAnswerDao,
) : IPersonStoryAnswerRepository {

    override fun getAllPersonStoryAnswers(lang: String): ApiResponse<List<PersonStoryAnswerResponse>> {
        val answers = personStoryAnswerDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("person_story_answers_retrieved_successfully", lang)
        )
    }

    override fun getPersonStoryAnswerById(id: Int, lang: String): ApiResponse<PersonStoryAnswerResponse> {
        val answer = personStoryAnswerDao.findById(id)
        return ApiResponse(
            success = true,
            data = answer?.convertToResponse(),
            message = Localization.get("person_story_answer_retrieved_successfully", lang)
        )
    }

    override fun getPersonStoryAnswersByStoryId(storyId: Int, lang: String): ApiResponse<List<PersonStoryAnswerResponse>> {
        val answers = personStoryAnswerDao.findByStoryId(storyId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("person_story_answers_retrieved_successfully", lang)
        )
    }

    override fun getPersonStoryAnswersByUserId(userId: Int, lang: String): ApiResponse<List<PersonStoryAnswerResponse>> {
        val answers = personStoryAnswerDao.findByUserId(userId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("person_story_answers_retrieved_successfully", lang)
        )
    }

    override fun getPersonStoryAnswersByQuestionId(questionId: Int, lang: String): ApiResponse<List<PersonStoryAnswerResponse>> {
        val answers = personStoryAnswerDao.findByQuestionId(questionId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("person_story_answers_retrieved_successfully", lang)
        )
    }

    override fun createPersonStoryAnswer(request: CreatePersonStoryAnswerRequest, lang: String): ApiResponse<Int> {
        val id = personStoryAnswerDao.create(request.convertToPersonStoryAnswerDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("person_story_answer_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("person_story_answer_created_successfully", lang)
        )
    }

    override fun updatePersonStoryAnswer(id: Int, request: UpdatePersonStoryAnswerRequest, lang: String): ApiResponse<Nothing> {
        val updated = personStoryAnswerDao.update(request.convertToPersonStoryAnswerDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("person_story_answer_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_story_answer_updated_successfully", lang)
        )
    }

    override fun updatePersonStoryAnswerStatus(id: Int, request: UpdatePersonStoryAnswerStatusRequest, lang: String): ApiResponse<Nothing> {
        val updated = personStoryAnswerDao.updateStatus(statusToDto(id, AnswerStatus.valueOf(request.status)))

        if (!updated) throw IllegalArgumentException(Localization.get("person_story_answer_status_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_story_answer_status_updated_successfully", lang)
        )
    }

    override fun deletePersonStoryAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        val deleted = personStoryAnswerDao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("person_story_answer_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("person_story_answer_deleted_successfully", lang)
        )
    }

    private fun statusToDto(id: Int, status: AnswerStatus) = PersonStoryAnswerDto(
        id = id,
        storyId = 0,
        userId = 0,
        answered = "",
        status = status,
        questionId = 0
    )
}