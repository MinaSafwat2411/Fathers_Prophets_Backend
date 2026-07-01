package com.fathersprophets.backend.database.repository.escapeegyptanswer

import com.fathersprophets.backend.database.dao.EscapeEgyptAnswerDao
import com.fathersprophets.backend.database.dao.EscapeEgyptQuestionDao
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

    override fun createAnswer(request: CreateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {
        val existing = answerDao.findByQuestionIdAndUserId(request.escapeQuestionId, request.userId)
        if (existing != null) throw IllegalStateException(Localization.get("escape_egypt_answer_already_exists", lang))

        val question = questionDao.findById(request.escapeQuestionId)
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_question_not_found", lang))

        val status = gradeAnswer(request.answer, question.correctAnswer)

        val id = answerDao.create(
            EscapeEgyptAnswerDto(
                id = 0,
                escapeEgyptId = request.escapeEgyptId,
                escapeQuestionId = request.escapeQuestionId,
                userId = request.userId,
                answer = request.answer,
                status = status
            )
        )
        val created = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("escape_egypt_answer_created_successfully", lang)
        )
    }

    override fun updateAnswer(id: Int, request: UpdateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {
        val question = questionDao.findById(request.escapeQuestionId)
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_question_not_found", lang))

        val status = gradeAnswer(request.answer, question.correctAnswer)

        answerDao.update(
            EscapeEgyptAnswerDto(
                id = id,
                escapeEgyptId = request.escapeEgyptId,
                escapeQuestionId = request.escapeQuestionId,
                userId = request.userId,
                answer = request.answer,
                status = status
            )
        )
        val updated = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("escape_egypt_answer_updated_successfully", lang)
        )
    }

    override fun updateAnswerStatus(id: Int, request: UpdateEscapeEgyptAnswerStatusRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse> {
        answerDao.updateStatus(idToDto(id, AnswerStatus.valueOf(request.status)))
        val updated = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("escape_egypt_answer_status_updated_successfully", lang)
        )
    }

    override fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        answerDao.delete(idToDto(id, AnswerStatus.IS_FALSE))
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