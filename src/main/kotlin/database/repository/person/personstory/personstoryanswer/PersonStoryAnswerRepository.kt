package com.fathersprophets.backend.database.repository.person.personstory.personstoryanswer

import com.fathersprophets.backend.database.dao.PersonStoryAnswerDao
import com.fathersprophets.backend.database.dao.PersonStoryQuestionDao
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
    private val personStoryQuestionDao: PersonStoryQuestionDao
) : IPersonStoryAnswerRepository {

    override fun getAllPersonStoryAnswers(lang: String): ApiResponse<List<PersonStoryAnswerResponse>> {
        val answers = personStoryAnswerDao.findAll()
        val questions = personStoryQuestionDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse(questions.find { q -> q.id == it.questionId }?.correctAnswer) },
            message = Localization.get("person_story_answers_retrieved_successfully", lang)
        )
    }

    override fun getAllPersonStoryAnswersByUserIdAndStoryId(
        userId: Int,
        storyId: Int,
        lang: String
    ): ApiResponse<List<PersonStoryAnswerResponse>> {
        val answers = personStoryAnswerDao.findByUserIdAndStoryId(userId, storyId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("person_story_answers_retrieved_successfully", lang)
        )
    }

    override fun createPersonStoryAnswer(request: CreatePersonStoryAnswerRequest, lang: String): ApiResponse<PersonStoryAnswerResponse> {
        val created = personStoryAnswerDao.create(request.convertToPersonStoryAnswerDto())
            ?:throw IllegalArgumentException(Localization.get("person_story_answer_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = created.convertToResponse(),
            message = Localization.get("person_story_answer_created_successfully", lang)
        )
    }

    override fun updatePersonStoryAnswer(id: Int, request: UpdatePersonStoryAnswerRequest, lang: String): ApiResponse<PersonStoryAnswerResponse> {
        val updated = personStoryAnswerDao.update(request.convertToPersonStoryAnswerDto(id))
            ?:throw IllegalArgumentException(Localization.get("person_story_answer_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
            message = Localization.get("person_story_answer_updated_successfully", lang)
        )
    }

    override fun updatePersonStoryAnswerStatus(id: Int, request: UpdatePersonStoryAnswerStatusRequest, lang: String): ApiResponse<PersonStoryAnswerResponse> {
        val updated = personStoryAnswerDao.updateStatus(statusToDto(id, AnswerStatus.valueOf(request.status)))
            ?:throw IllegalArgumentException(Localization.get("person_story_answer_status_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
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