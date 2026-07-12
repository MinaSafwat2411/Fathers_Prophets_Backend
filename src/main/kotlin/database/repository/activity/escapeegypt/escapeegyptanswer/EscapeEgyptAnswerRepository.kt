package com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptanswer

import com.fathersprophets.backend.database.dao.activity.escapeegypt.EscapeEgyptAnswerDao
import com.fathersprophets.backend.database.dao.activity.escapeegypt.EscapeEgyptQuestionDao
import com.fathersprophets.backend.database.tables.AnswerStatus
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
            data = answers.map { it.convertToResponse() },
            message = Localization.get("escape_egypt_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswerById(id: Int, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {
        val answer = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = answer?.convertToResponse(),
            message = Localization.get("escape_egypt_answer_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByEscapeEgyptId(escapeEgyptId: Int, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>> {
        val answers = answerDao.findByEscapeEgyptId(escapeEgyptId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("escape_egypt_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByQuestionId(questionId: Int, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>> {
        val answers = answerDao.findByQuestionId(questionId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("escape_egypt_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByUserId(userId: Int, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>> {
        val answers = answerDao.findByUserId(userId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("escape_egypt_answers_retrieved_successfully", lang)
        )
    }

    override fun createAnswer(request: CreateEscapeEgyptAnswerRequest, lang: String): ApiResponse<Int> {

        val id = answerDao.create(request.convertToDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("escape_egypt_answer_creation_failed", lang))

        val question = questionDao.findById(request.escapeQuestionId)
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_question_not_found", lang))

        val status = gradeAnswer(request.answer, question.correctAnswer)

        answerDao.updateStatus(
            EscapeEgyptAnswerDto(
                id = id,
                escapeEgyptId = request.escapeEgyptId,
                escapeQuestionId = request.escapeQuestionId,
                userId = request.userId,
                answer = request.answer,
                status = status
            )
        )

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("escape_egypt_answer_created_successfully", lang)
        )
    }

    override fun updateAnswer(id: Int, request: UpdateEscapeEgyptAnswerRequest, lang: String): ApiResponse<Nothing> {
        val question = questionDao.findById(request.escapeQuestionId)
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_question_not_found", lang))

        val status = gradeAnswer(request.answer, question.correctAnswer)

        val updated = answerDao.update(
            EscapeEgyptAnswerDto(
                id = id,
                escapeEgyptId = request.escapeEgyptId,
                escapeQuestionId = request.escapeQuestionId,
                userId = request.userId,
                answer = request.answer,
                status = status
            )
        )
        if (!updated) throw IllegalArgumentException(Localization.get("escape_egypt_answer_not_found", lang))


        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("escape_egypt_answer_updated_successfully", lang)
        )
    }

    override fun updateAnswerStatus(id: Int, request: UpdateEscapeEgyptAnswerStatusRequest, lang: String): ApiResponse<Nothing> {
        val updated = answerDao.updateStatus(idToDto(id, AnswerStatus.valueOf(request.status)))

        if (!updated) throw IllegalArgumentException(Localization.get("escape_egypt_answer_not_found", lang))

        return ApiResponse(
            success = true,
            data = null,
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

    private fun gradeAnswer(answer: String, correctAnswer: String) =
        if (answer.trim().equals(correctAnswer.trim(), ignoreCase = true)) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE

    private fun idToDto(id: Int, status: AnswerStatus) = EscapeEgyptAnswerDto(
        id = id,
        escapeEgyptId = 0,
        escapeQuestionId = 0,
        userId = 0,
        answer = "",
        status = status
    )
}