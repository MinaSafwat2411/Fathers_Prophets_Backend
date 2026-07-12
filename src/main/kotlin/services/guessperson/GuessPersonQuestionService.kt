package com.fathersprophets.backend.services.guessperson

import com.fathersprophets.backend.database.repository.person.guessperson.IGuessPersonQuestionRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.guessperson.CreateGuessPersonQuestionRequest
import com.fathersprophets.backend.models.guessperson.GuessPersonQuestionResponse
import com.fathersprophets.backend.models.guessperson.UpdateGuessPersonQuestionRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class GuessPersonQuestionService(
    private val repository: IGuessPersonQuestionRepository
) : IGuessPersonQuestionService {

    override fun getAllQuestions(lang: String): ApiResponse<List<GuessPersonQuestionResponse>> {
        return repository.getAllQuestions(lang)
    }

    override fun getQuestionById(id: Int?, lang: String): ApiResponse<GuessPersonQuestionResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("guess_person_question_id_required", lang))
        return repository.getQuestionById(id, lang)
    }

    override fun createQuestion(
        request: CreateGuessPersonQuestionRequest,
        lang: String
    ): ApiResponse<GuessPersonQuestionResponse> {
        validateRequired(
            request.question to "question",
            request.correctAnswer to "correctAnswer",
            lang = lang
        )
        return repository.createQuestion(request, lang)
    }

    override fun updateQuestion(
        id: Int?,
        request: UpdateGuessPersonQuestionRequest,
        lang: String
    ): ApiResponse<GuessPersonQuestionResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("guess_person_question_id_required", lang))
        validateRequired(
            request.question to "question",
            request.correctAnswer to "correctAnswer",
            lang = lang
        )
        return repository.updateQuestion(id, request, lang)
    }

    override fun deleteQuestion(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("guess_person_question_id_required", lang))
        return repository.deleteQuestion(id, lang)
    }
}