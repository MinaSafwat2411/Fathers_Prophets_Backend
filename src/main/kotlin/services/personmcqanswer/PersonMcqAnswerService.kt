package com.fathersprophets.backend.services.personmcqanswer

import com.fathersprophets.backend.database.repository.personmcqanswer.IPersonMcqAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personmcqanswer.CreatePersonMcqAnswerRequest
import com.fathersprophets.backend.models.personmcqanswer.PersonMcqAnswerResponse
import com.fathersprophets.backend.models.personmcqanswer.UpdateMcqAnswerStatusRequest
import com.fathersprophets.backend.models.personmcqanswer.UpdatePersonMcqAnswerRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonMcqAnswerService(
    private val personMcqAnswerRepository: IPersonMcqAnswerRepository
) : IPersonMcqAnswerService {

    override fun getAllPersonMcqAnswers(lang: String): ApiResponse<List<PersonMcqAnswerResponse>> {
        return personMcqAnswerRepository.getAllPersonMcqAnswers(lang)
    }

    override fun getPersonMcqAnswerById(id: Int?, lang: String): ApiResponse<PersonMcqAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_mcq_answer_id_required", lang))
        return personMcqAnswerRepository.getPersonMcqAnswerById(id, lang)
    }

    override fun getPersonMcqAnswersByQuestionId(questionId: Int?, lang: String): ApiResponse<List<PersonMcqAnswerResponse>> {
        if (questionId == null) throw IllegalArgumentException(Localization.get("question_id_required", lang))
        return personMcqAnswerRepository.getPersonMcqAnswersByQuestionId(questionId, lang)
    }

    override fun getPersonMcqAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<PersonMcqAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return personMcqAnswerRepository.getPersonMcqAnswersByUserId(userId, lang)
    }

    override fun createPersonMcqAnswer(request: CreatePersonMcqAnswerRequest, lang: String): ApiResponse<PersonMcqAnswerResponse> {
        validateRequired(
            request.answer to "answer",
            lang = lang
        )
        return personMcqAnswerRepository.createPersonMcqAnswer(request, lang)
    }

    override fun updatePersonMcqAnswer(id: Int?, request: UpdatePersonMcqAnswerRequest, lang: String): ApiResponse<PersonMcqAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_mcq_answer_id_required", lang))
        validateRequired(
            request.answer to "answer",
            request.status to "status",
            lang = lang
        )
        return personMcqAnswerRepository.updatePersonMcqAnswer(id, request, lang)
    }

    override fun updatePersonMcqAnswerStatus(id: Int?, request: UpdateMcqAnswerStatusRequest, lang: String): ApiResponse<PersonMcqAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_mcq_answer_id_required", lang))
        validateRequired(
            request.status to "status",
            lang = lang
        )
        return personMcqAnswerRepository.updatePersonMcqAnswerStatus(id, request, lang)
    }

    override fun deletePersonMcqAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_mcq_answer_id_required", lang))
        return personMcqAnswerRepository.deletePersonMcqAnswer(id, lang)
    }
}