package com.fathersprophets.backend.services.personstoryquestion

import com.fathersprophets.backend.database.repository.personstoryquestion.IPersonStoryQuestionRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personstoryquestion.CreatePersonStoryQuestionRequest
import com.fathersprophets.backend.models.personstoryquestion.PersonStoryQuestionResponse
import com.fathersprophets.backend.models.personstoryquestion.UpdatePersonStoryQuestionRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonStoryQuestionService(
    private val personStoryQuestionRepository: IPersonStoryQuestionRepository
) : IPersonStoryQuestionService {

    override fun getAllPersonStoryQuestions(lang: String): ApiResponse<List<PersonStoryQuestionResponse>> {
        return personStoryQuestionRepository.getAllPersonStoryQuestions(lang)
    }

    override fun getPersonStoryQuestionById(id: Int?, lang: String): ApiResponse<PersonStoryQuestionResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_question_id_required", lang))
        return personStoryQuestionRepository.getPersonStoryQuestionById(id, lang)
    }

    override fun getPersonStoryQuestionsByStoryId(storyId: Int?, lang: String): ApiResponse<List<PersonStoryQuestionResponse>> {
        if (storyId == null) throw IllegalArgumentException(Localization.get("person_story_id_required", lang))
        return personStoryQuestionRepository.getPersonStoryQuestionsByStoryId(storyId, lang)
    }

    override fun createPersonStoryQuestion(request: CreatePersonStoryQuestionRequest, lang: String): ApiResponse<PersonStoryQuestionResponse> {
        validateRequired(
            request.question to "question",
            lang = lang
        )
        return personStoryQuestionRepository.createPersonStoryQuestion(request, lang)
    }

    override fun updatePersonStoryQuestion(id: Int?, request: UpdatePersonStoryQuestionRequest, lang: String): ApiResponse<PersonStoryQuestionResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_question_id_required", lang))
        validateRequired(
            request.question to "question",
            lang = lang
        )
        return personStoryQuestionRepository.updatePersonStoryQuestion(id, request, lang)
    }

    override fun deletePersonStoryQuestion(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_story_question_id_required", lang))
        return personStoryQuestionRepository.deletePersonStoryQuestion(id, lang)
    }
}