package com.fathersprophets.backend.services.activity.escapeegypt.escapeegyptquestion

import com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptquestion.IEscapeEgyptQuestionRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegyptquestion.CreateEscapeEgyptQuestionRequest
import com.fathersprophets.backend.models.escapeegyptquestion.EscapeEgyptQuestionResponse
import com.fathersprophets.backend.models.escapeegyptquestion.UpdateEscapeEgyptQuestionRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class EscapeEgyptQuestionService(
    private val repository: IEscapeEgyptQuestionRepository
) : IEscapeEgyptQuestionService {

    override fun getAllQuestions(lang: String): ApiResponse<List<EscapeEgyptQuestionResponse>> {
        return repository.getAllQuestions(lang)
    }

    override fun getQuestionById(id: Int?, lang: String): ApiResponse<EscapeEgyptQuestionResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("escape_egypt_question_id_required", lang))
        return repository.getQuestionById(id, lang)
    }

    override fun getQuestionsByEscapeEgyptId(escapeEgyptId: Int?, lang: String): ApiResponse<List<EscapeEgyptQuestionResponse>> {
        if (escapeEgyptId == null) throw IllegalArgumentException(Localization.get("escape_egypt_id_required", lang))
        return repository.getQuestionsByEscapeEgyptId(escapeEgyptId, lang)
    }

    override fun createQuestion(request: CreateEscapeEgyptQuestionRequest, lang: String): ApiResponse<EscapeEgyptQuestionResponse> {
        validateRequired(
            request.escapeEgyptId to "escapeEgyptId",
            request.question to "question",
            request.correctAnswer to "correctAnswer",
            lang = lang
        )
        return repository.createQuestion(request, lang)
    }

    override fun updateQuestion(id: Int?, request: UpdateEscapeEgyptQuestionRequest, lang: String): ApiResponse<EscapeEgyptQuestionResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("escape_egypt_question_id_required", lang))
        validateRequired(
            request.escapeEgyptId to "escapeEgyptId",
            request.question to "question",
            request.correctAnswer to "correctAnswer",
            lang = lang
        )
        return repository.updateQuestion(id, request, lang)
    }

    override fun deleteQuestion(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("escape_egypt_question_id_required", lang))
        return repository.deleteQuestion(id, lang)
    }
}