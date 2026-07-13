package com.fathersprophets.backend.services.person.personstory.personstoryanswer

import com.fathersprophets.backend.database.repository.person.personstoryanswer.IPersonStoryAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstoryanswer.CreatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.PersonStoryAnswerResponse
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerRequest
import com.fathersprophets.backend.models.personstoryanswer.UpdatePersonStoryAnswerStatusRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonStoryAnswerService(
    private val personStoryAnswerRepository: IPersonStoryAnswerRepository
) : IPersonStoryAnswerService {

    override fun getAllPersonStoryAnswers(lang: String): ApiResponse<List<PersonStoryAnswerResponse>> {
        return personStoryAnswerRepository.getAllPersonStoryAnswers(lang)
    }

    override fun getPersonStoryAnswerById(id: Int?, lang: String): ApiResponse<PersonStoryAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_answer_id_required", lang))
        return personStoryAnswerRepository.getPersonStoryAnswerById(id, lang)
    }

    override fun getPersonStoryAnswersByStoryId(storyId: Int?, lang: String): ApiResponse<List<PersonStoryAnswerResponse>> {
        if (storyId == null) throw IllegalArgumentException(Localization.get("person_story_id_required", lang))
        return personStoryAnswerRepository.getPersonStoryAnswersByStoryId(storyId, lang)
    }

    override fun getPersonStoryAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<PersonStoryAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return personStoryAnswerRepository.getPersonStoryAnswersByUserId(userId, lang)
    }

    override fun getPersonStoryAnswersByQuestionId(questionId: Int?, lang: String): ApiResponse<List<PersonStoryAnswerResponse>> {
        if (questionId == null) throw IllegalArgumentException(Localization.get("question_id_required", lang))
        return personStoryAnswerRepository.getPersonStoryAnswersByQuestionId(questionId, lang)
    }

    override fun createPersonStoryAnswer(request: CreatePersonStoryAnswerRequest, lang: String): ApiResponse<Int> {
        validateRequired(
            request.answered to "answered",
            lang = lang
        )
        return personStoryAnswerRepository.createPersonStoryAnswer(request, lang)
    }

    override fun updatePersonStoryAnswer(id: Int?, request: UpdatePersonStoryAnswerRequest, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_answer_id_required", lang))
        validateRequired(
            request.answered to "answered",
            request.status to "status",
            lang = lang
        )
        return personStoryAnswerRepository.updatePersonStoryAnswer(id, request, lang)
    }

    override fun updatePersonStoryAnswerStatus(id: Int?, request: UpdatePersonStoryAnswerStatusRequest, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_answer_id_required", lang))
        validateRequired(
            request.status to "status",
            lang = lang
        )
        return personStoryAnswerRepository.updatePersonStoryAnswerStatus(id, request, lang)
    }

    override fun deletePersonStoryAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_answer_id_required", lang))
        return personStoryAnswerRepository.deletePersonStoryAnswer(id, lang)
    }
}