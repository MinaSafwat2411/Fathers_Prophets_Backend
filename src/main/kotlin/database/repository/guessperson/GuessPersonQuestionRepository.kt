package com.fathersprophets.backend.database.repository.guessperson

import com.fathersprophets.backend.database.dao.GuessPersonQuestionDao
import com.fathersprophets.backend.database.tables.McqCorrectAnswer
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.GuessPersonQuestionDto
import com.fathersprophets.backend.models.guessperson.CreateGuessPersonQuestionRequest
import com.fathersprophets.backend.models.guessperson.GuessPersonChoice
import com.fathersprophets.backend.models.guessperson.GuessPersonQuestionResponse
import com.fathersprophets.backend.models.guessperson.UpdateGuessPersonQuestionRequest
import com.fathersprophets.backend.utils.Localization

class GuessPersonQuestionRepository(
    private val dao: GuessPersonQuestionDao
) : IGuessPersonQuestionRepository {

    override fun getAllQuestions(lang: String): ApiResponse<List<GuessPersonQuestionResponse>> {
        val questions = dao.findAll()
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToResponse() },
            message = Localization.get("guess_person_questions_retrieved_successfully", lang)
        )
    }

    override fun getQuestionById(id: Int, lang: String): ApiResponse<GuessPersonQuestionResponse> {
        val question = dao.findById(id)
        return ApiResponse(
            success = true,
            data = question?.convertToResponse(),
            message = Localization.get("guess_person_question_retrieved_successfully", lang)
        )
    }

    override fun createQuestion(
        request: CreateGuessPersonQuestionRequest,
        lang: String
    ): ApiResponse<GuessPersonQuestionResponse> {
        val id = dao.create(request.convertToDto())
        val created = dao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("guess_person_question_created_successfully", lang)
        )
    }

    override fun updateQuestion(
        id: Int,
        request: UpdateGuessPersonQuestionRequest,
        lang: String
    ): ApiResponse<GuessPersonQuestionResponse> {
        dao.update(request.convertToDto(id))
        val updated = dao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("guess_person_question_updated_successfully", lang)
        )
    }

    override fun deleteQuestion(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("guess_person_question_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = GuessPersonQuestionDto(
        id = id,
        question = "",
        correctPersonId = 0,
        difficulty = null,
        first = GuessPersonChoice(0, ""),
        second = GuessPersonChoice(0, ""),
        third = GuessPersonChoice(0, ""),
        fourth = GuessPersonChoice(0, ""),
        correctAnswer = McqCorrectAnswer.`1`
    )
}