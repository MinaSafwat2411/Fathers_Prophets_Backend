package com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptquestion

import com.fathersprophets.backend.database.dao.activity.escapeegypt.EscapeEgyptQuestionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegyptquestion.CreateEscapeEgyptQuestionRequest
import com.fathersprophets.backend.models.escapeegyptquestion.EscapeEgyptQuestionResponse
import com.fathersprophets.backend.models.escapeegyptquestion.UpdateEscapeEgyptQuestionRequest
import com.fathersprophets.backend.utils.Localization

class EscapeEgyptQuestionRepository(
    private val dao: EscapeEgyptQuestionDao
) : IEscapeEgyptQuestionRepository {

    override fun getAllQuestions(lang: String): ApiResponse<List<EscapeEgyptQuestionResponse>> {
        val questions = dao.findAll()
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToResponse() },
            message = Localization.get("escape_egypt_questions_retrieved_successfully", lang)
        )
    }

    override fun getQuestionById(id: Int, lang: String): ApiResponse<EscapeEgyptQuestionResponse> {
        val question = dao.findById(id)
        return ApiResponse(
            success = true,
            data = question?.convertToResponse(),
            message = Localization.get("escape_egypt_question_retrieved_successfully", lang)
        )
    }

    override fun getQuestionsByEscapeEgyptId(escapeEgyptId: Int, lang: String): ApiResponse<List<EscapeEgyptQuestionResponse>> {
        val questions = dao.findByEscapeEgyptId(escapeEgyptId)
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToResponse() },
            message = Localization.get("escape_egypt_questions_retrieved_successfully", lang)
        )
    }

    override fun createQuestion(request: CreateEscapeEgyptQuestionRequest, lang: String): ApiResponse<EscapeEgyptQuestionResponse> {

        val created = dao.create(request.convertToDto())
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_question_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = created.convertToResponse(),
            message = Localization.get("escape_egypt_question_created_successfully", lang)
        )
    }

    override fun updateQuestion(id: Int, request: UpdateEscapeEgyptQuestionRequest, lang: String): ApiResponse<EscapeEgyptQuestionResponse> {
        val  update = dao.update(request.convertToDto(id))
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_question_update_failed", lang))

        return ApiResponse(
            success = true,
            data = update.convertToResponse(),
            message = Localization.get("escape_egypt_question_updated_successfully", lang)
        )
    }

    override fun deleteQuestion(id: Int, lang: String): ApiResponse<Nothing> {

        val deleted = dao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("escape_egypt_question_not_found", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("escape_egypt_question_deleted_successfully", lang)
        )
    }
}