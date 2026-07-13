package com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptanswer

import com.fathersprophets.backend.database.dao.activity.escapeegypt.EscapeEgyptAnswerDao
import com.fathersprophets.backend.database.dao.activity.escapeegypt.EscapeEgyptQuestionDao
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.EscapeEgyptAnswerDto
import com.fathersprophets.backend.models.escapeegyptanswer.CreateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.EscapeEgyptAnswerResponse
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerStatusRequest
import com.fathersprophets.backend.utils.Localization

class EscapeEgyptAnswerRepository(
    private val answerDao: EscapeEgyptAnswerDao,
    private val questionDao: EscapeEgyptQuestionDao
) : IEscapeEgyptAnswerRepository {

    override fun getAllAnswers(lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>> {
        val answers = answerDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse().copy(correctAnswer = questionDao.findById(it.escapeQuestionId)?.correctAnswer ?: "") },
            message = Localization.get("escape_egypt_answers_retrieved_successfully", lang)
        )
    }


    override fun getAnswersByEscapeEgyptId(escapeEgyptId: Int, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>> {
        val answers = answerDao.findByEscapeEgyptId(escapeEgyptId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse().copy(correctAnswer = questionDao.findById(it.escapeQuestionId)?.correctAnswer ?: "") },
            message = Localization.get("escape_egypt_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByUserIdAndEscapeEgyptId(
        userId: Int,
        escapeEgyptId: Int,
        lang: String
    ): ApiResponse<List<EscapeEgyptAnswerResponse>> {

        val answers = answerDao.findByUserIdAndEscapeEgyptId(userIdToDto(userId, escapeEgyptId))

        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("escape_egypt_answers_retrieved_successfully", lang)
        )
    }

    override fun createAnswer(request: CreateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {
        val created = answerDao.create(request.convertToDto())
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_answer_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = created.convertToResponse(),
            message = Localization.get("escape_egypt_answer_created_successfully", lang)
        )
    }

    override fun updateAnswer(id: Int, request: UpdateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {

        val updated = answerDao.update(request.convertToDto(id))
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_answer_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
            message = Localization.get("escape_egypt_answer_updated_successfully", lang)
        )
    }

    override fun updateAnswerStatus(id: Int, request: UpdateEscapeEgyptAnswerStatusRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {

        val updated = answerDao.updateStatus(idToDto(id, AnswerStatus.valueOf(request.status)))
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_answer_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
            message = Localization.get("escape_egypt_answer_status_updated_successfully", lang)
        )
    }

    override fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        val deleted = answerDao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("escape_egypt_answer_not_found", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("escape_egypt_answer_deleted_successfully", lang)
        )
    }

    private fun userIdToDto(userId: Int, escapeEgyptId: Int) = EscapeEgyptAnswerDto(
        id = 0,
        escapeEgyptId = escapeEgyptId,
        escapeQuestionId = 0,
        userId = userId,
        answer = "",
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
    )

    private fun idToDto(id: Int, status: AnswerStatus) = EscapeEgyptAnswerDto(
        id = id,
        escapeEgyptId = 0,
        escapeQuestionId = 0,
        userId = 0,
        answer = "",
        status = status
    )
}