package com.fathersprophets.backend.services.person.personstory.personstoryanswer

import com.fathersprophets.backend.database.repository.person.personstory.personstoryanswer.IPersonStoryAnswerRepository
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

    override fun getPersonStoryAnswersByUserAndStoryId(
        storyId: Int?,
        userId: Int?,
        lang: String
    ): ApiResponse<List<PersonStoryAnswerResponse>> {
        if (storyId == null || userId == null) throw IllegalArgumentException(Localization.get("story_id_or_user_id_required", lang))
        return personStoryAnswerRepository.getAllPersonStoryAnswersByUserIdAndStoryId(userId, storyId, lang)
    }

    override fun createPersonStoryAnswer(request: CreatePersonStoryAnswerRequest, lang: String): ApiResponse<PersonStoryAnswerResponse> {
        validateRequired(
            request.answered to "answered",
            lang = lang
        )
        return personStoryAnswerRepository.createPersonStoryAnswer(request, lang)
    }

    override fun updatePersonStoryAnswer(id: Int?, request: UpdatePersonStoryAnswerRequest, lang: String): ApiResponse<PersonStoryAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_answer_id_required", lang))
        validateRequired(
            request.answered to "answered",
            request.status to "status",
            lang = lang
        )
        return personStoryAnswerRepository.updatePersonStoryAnswer(id, request, lang)
    }

    override fun updatePersonStoryAnswerStatus(id: Int?, request: UpdatePersonStoryAnswerStatusRequest, lang: String): ApiResponse<PersonStoryAnswerResponse> {
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