package com.fathersprophets.backend.database.repository.guesspersonanswer

import com.fathersprophets.backend.database.dao.GuessPersonAnswerDao
import com.fathersprophets.backend.database.dao.GuessPersonQuestionDao
import com.fathersprophets.backend.database.tables.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.GuessPersonAnswerDto
import com.fathersprophets.backend.models.guesspersonanswer.CreateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.GuessPersonAnswerResponse
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerStatusRequest
import com.fathersprophets.backend.utils.Localization

class GuessPersonAnswerRepository(
    private val answerDao: GuessPersonAnswerDao,
    private val questionDao: GuessPersonQuestionDao
) : IGuessPersonAnswerRepository {

    override fun getAllAnswers(lang: String): ApiResponse<List<GuessPersonAnswerResponse>> {
        val answers = answerDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("guess_person_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswerById(id: Int, lang: String): ApiResponse<GuessPersonAnswerResponse> {
        val answer = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = answer?.convertToResponse(),
            message = Localization.get("guess_person_answer_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByQuestionId(questionId: Int, lang: String): ApiResponse<List<GuessPersonAnswerResponse>> {
        val answers = answerDao.findByQuestionId(questionId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("guess_person_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByUserId(userId: Int, lang: String): ApiResponse<List<GuessPersonAnswerResponse>> {
        val answers = answerDao.findByUserId(userId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("guess_person_answers_retrieved_successfully", lang)
        )
    }

    override fun createAnswer(request: CreateGuessPersonAnswerRequest, lang: String): ApiResponse<GuessPersonAnswerResponse> {
        val existing = answerDao.findByQuestionIdAndUserId(request.questionId, request.userId)
        if (existing != null) throw IllegalStateException(Localization.get("guess_person_answer_already_exists", lang))

        val question = questionDao.findById(request.questionId)
            ?: throw IllegalArgumentException(Localization.get("guess_person_question_not_found", lang))

        val status = if (request.personId == question.correctPersonId) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE

        val id = answerDao.create(
            GuessPersonAnswerDto(
                id = 0,
                questionId = request.questionId,
                userId = request.userId,
                personId = request.personId,
                status = status
            )
        )
        val created = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("guess_person_answer_created_successfully", lang)
        )
    }

    override fun updateAnswer(id: Int, request: UpdateGuessPersonAnswerRequest, lang: String): ApiResponse<GuessPersonAnswerResponse> {
        val question = questionDao.findById(request.questionId)
            ?: throw IllegalArgumentException(Localization.get("guess_person_question_not_found", lang))

        val status = if (request.personId == question.correctPersonId) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE

        answerDao.update(
            GuessPersonAnswerDto(
                id = id,
                questionId = request.questionId,
                userId = request.userId,
                personId = request.personId,
                status = status
            )
        )
        val updated = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("guess_person_answer_updated_successfully", lang)
        )
    }

    override fun updateAnswerStatus(id: Int, request: UpdateGuessPersonAnswerStatusRequest, lang: String): ApiResponse<GuessPersonAnswerResponse> {
        answerDao.updateStatus(idToDto(id, AnswerStatus.valueOf(request.status)))
        val updated = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("guess_person_answer_status_updated_successfully", lang)
        )
    }

    override fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        answerDao.delete(idToDto(id, AnswerStatus.IS_FALSE))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("guess_person_answer_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int, status: AnswerStatus) = GuessPersonAnswerDto(
        id = id,
        questionId = 0,
        userId = 0,
        personId = 0,
        status = status
    )
}