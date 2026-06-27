package com.fathersprophets.backend.services.personanswer

import com.fathersprophets.backend.database.repository.personanswer.IPersonAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personanswer.CreatePersonAnswerRequest
import com.fathersprophets.backend.models.personanswer.PersonAnswerResponse
import com.fathersprophets.backend.models.personanswer.UpdateAnswerStatusRequest
import com.fathersprophets.backend.models.personanswer.UpdatePersonAnswerRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonAnswerService(
    private val personAnswerRepository: IPersonAnswerRepository
) : IPersonAnswerService {

    override fun getAllPersonAnswers(lang: String): ApiResponse<List<PersonAnswerResponse>> {
        return personAnswerRepository.getAllPersonAnswers(lang)
    }

    override fun getPersonAnswerById(id: Int?, lang: String): ApiResponse<PersonAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_answer_id_required", lang))
        return personAnswerRepository.getPersonAnswerById(id, lang)
    }

    override fun getPersonAnswersByQuestionId(questionId: Int?, lang: String): ApiResponse<List<PersonAnswerResponse>> {
        if (questionId == null) throw IllegalArgumentException(Localization.get("question_id_required", lang))
        return personAnswerRepository.getPersonAnswersByQuestionId(questionId, lang)
    }

    override fun getPersonAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<PersonAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return personAnswerRepository.getPersonAnswersByUserId(userId, lang)
    }

    override fun createPersonAnswer(request: CreatePersonAnswerRequest, lang: String): ApiResponse<PersonAnswerResponse> {
        validateRequired(
            request.answer to "answer",
            lang = lang
        )
        return personAnswerRepository.createPersonAnswer(request, lang)
    }

    override fun updatePersonAnswer(id: Int?, request: UpdatePersonAnswerRequest, lang: String): ApiResponse<PersonAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_answer_id_required", lang))
        validateRequired(
            request.answer to "answer",
            request.status to "status",
            lang = lang
        )
        return personAnswerRepository.updatePersonAnswer(id, request, lang)
    }

    override fun updatePersonAnswerStatus(id: Int?, request: UpdateAnswerStatusRequest, lang: String): ApiResponse<PersonAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_answer_id_required", lang))
        validateRequired(
            request.status to "status",
            lang = lang
        )
        return personAnswerRepository.updatePersonAnswerStatus(id, request, lang)
    }

    override fun deletePersonAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_answer_id_required", lang))
        return personAnswerRepository.deletePersonAnswer(id, lang)
    }
}