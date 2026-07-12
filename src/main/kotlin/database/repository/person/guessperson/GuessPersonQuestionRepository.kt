package com.fathersprophets.backend.database.repository.person.guessperson

import com.fathersprophets.backend.database.dao.person.guessperson.GuessPersonQuestionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.guessperson.CreateGuessPersonQuestionRequest
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
    ): ApiResponse<Int> {
        val id = dao.create(request.convertToDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("guess_person_question_creation_failed", lang))


        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("guess_person_question_created_successfully", lang)
        )
    }

    override fun updateQuestion(
        id: Int,
        request: UpdateGuessPersonQuestionRequest,
        lang: String
    ): ApiResponse<Nothing> {
        val updated = dao.update(request.convertToDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("guess_person_question_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("guess_person_question_updated_successfully", lang)
        )
    }

    override fun deleteQuestion(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(id)
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("guess_person_question_deleted_successfully", lang)
        )
    }
}