package com.fathersprophets.backend.services.person.mcq.personmcqanswer

import com.fathersprophets.backend.database.repository.person.personmcq.personmcqanswer.IPersonMcqAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personmcqanswer.CreatePersonMcqAnswerRequest
import com.fathersprophets.backend.models.personmcqanswer.PersonMcqAnswerResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonMcqAnswerService(
    private val personMcqAnswerRepository: IPersonMcqAnswerRepository
) : IPersonMcqAnswerService {

    override fun getAllPersonMcqAnswers(lang: String): ApiResponse<List<PersonMcqAnswerResponse>> {
        return personMcqAnswerRepository.getAllPersonMcqAnswers(lang)
    }

    override fun getPersonMcqAnswersByUserIdAndQuestionId(
        userId: Int?,
        questionId: Int?,
        lang: String
    ): ApiResponse<List<PersonMcqAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        if (questionId == null) throw IllegalArgumentException(Localization.get("question_id_required", lang))

        return personMcqAnswerRepository.getPersonMcqAnswersByUserIdAndQuestionId(userId, questionId, lang)
    }

    override fun createPersonMcqAnswer(request: CreatePersonMcqAnswerRequest, lang: String): ApiResponse<PersonMcqAnswerResponse> {
        validateRequired(
            request.answer to "answer",
            request.questionId to "questionId",
            request.userId to "userId",
            lang = lang
        )
        return personMcqAnswerRepository.createPersonMcqAnswer(request, lang)
    }


    override fun deletePersonMcqAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_mcq_answer_id_required", lang))
        return personMcqAnswerRepository.deletePersonMcqAnswer(id, lang)
    }
}