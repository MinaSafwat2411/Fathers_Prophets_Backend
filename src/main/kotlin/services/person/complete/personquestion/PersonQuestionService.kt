package com.fathersprophets.backend.services.person.complete.personquestion

import com.fathersprophets.backend.database.repository.person.personquestion.IPersonQuestionRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.personquestion.CreateQuestionRequest
import com.fathersprophets.backend.models.personquestion.PersonQuestionResponse
import com.fathersprophets.backend.models.personquestion.UpdateQuestionRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class PersonQuestionService(
    private val personQuestionRepository: IPersonQuestionRepository
) : IPersonQuestionService {

    override fun getAllPersonQuestions(lang: String): ApiResponse<List<PersonQuestionResponse>> {
        return personQuestionRepository.getAllPersonQuestions(lang)
    }

    override fun getPersonQuestionsByPersonId(personId: Int?, lang: String): ApiResponse<List<PersonQuestionResponse>> {
        if (personId == null) throw IllegalArgumentException(Localization.get("person_id_required", lang))
        return personQuestionRepository.getPersonQuestionsByPersonId(personId, lang)
    }

    override fun createPersonQuestion(request: CreateQuestionRequest, lang: String): ApiResponse<PersonQuestionResponse> {
        validateRequired(
            request.question to "question",
            request.type to "type",
            lang = lang
        )
        return personQuestionRepository.createPersonQuestion(request, lang)
    }

    override fun updatePersonQuestion(id: Int?, request: UpdateQuestionRequest, lang: String): ApiResponse<PersonQuestionResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_question_id_required", lang))
        validateRequired(
            request.question to "question",
            request.type to "type",
            lang = lang
        )
        return personQuestionRepository.updatePersonQuestion(id, request, lang)
    }

    override fun deletePersonQuestion(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("person_question_id_required", lang))
        return personQuestionRepository.deletePersonQuestion(id, lang)
    }
}