package com.fathersprophets.backend.services.escapeegyptanswer

import com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptanswer.IEscapeEgyptAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegyptanswer.CreateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.EscapeEgyptAnswerResponse
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerStatusRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class EscapeEgyptAnswerService(
    private val repository: IEscapeEgyptAnswerRepository
) : IEscapeEgyptAnswerService {

    override fun getAllAnswers(lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>> {
        return repository.getAllAnswers(lang)
    }

    override fun getAnswerById(id: Int?, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("escape_egypt_answer_id_required", lang))
        return repository.getAnswerById(id, lang)
    }

    override fun getAnswersByEscapeEgyptId(escapeEgyptId: Int?, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>> {
        if (escapeEgyptId == null) throw IllegalArgumentException(Localization.get("escape_egypt_id_required", lang))
        return repository.getAnswersByEscapeEgyptId(escapeEgyptId, lang)
    }

    override fun getAnswersByQuestionId(questionId: Int?, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>> {
        if (questionId == null) throw IllegalArgumentException(Localization.get("question_id_required", lang))
        return repository.getAnswersByQuestionId(questionId, lang)
    }

    override fun getAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return repository.getAnswersByUserId(userId, lang)
    }

    override fun createAnswer(request: CreateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {
        validateRequired(
            request.escapeEgyptId to "escapeEgyptId",
            request.escapeQuestionId to "escapeQuestionId",
            request.userId to "userId",
            request.answer to "answer",
            lang = lang
        )
        return repository.createAnswer(request, lang)
    }

    override fun updateAnswer(id: Int?, request: UpdateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("escape_egypt_answer_id_required", lang))
        validateRequired(
            request.escapeEgyptId to "escapeEgyptId",
            request.escapeQuestionId to "escapeQuestionId",
            request.userId to "userId",
            request.answer to "answer",
            lang = lang
        )
        return repository.updateAnswer(id, request, lang)
    }

    override fun updateAnswerStatus(id: Int?, request: UpdateEscapeEgyptAnswerStatusRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("escape_egypt_answer_id_required", lang))
        validateRequired(request.status to "status", lang = lang)
        return repository.updateAnswerStatus(id, request, lang)
    }

    override fun deleteAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("escape_egypt_answer_id_required", lang))
        return repository.deleteAnswer(id, lang)
    }
}