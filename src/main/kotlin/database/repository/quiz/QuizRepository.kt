package com.fathersprophets.backend.database.repository.quiz

import com.fathersprophets.backend.database.dao.quiz.QuizDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quiz.CreateQuizRequest
import com.fathersprophets.backend.models.quiz.QuizResponse
import com.fathersprophets.backend.models.quiz.UpdateQuizRequest
import com.fathersprophets.backend.utils.Localization

class QuizRepository(
    private val dao: QuizDao
) : IQuizRepository {

    override fun getAllQuizzes(lang: String): ApiResponse<List<QuizResponse>> {
        val quizzes = dao.findAll()
        return ApiResponse(
            success = true,
            data = quizzes.map { it.convertToResponse() },
            message = Localization.get("quizzes_retrieved_successfully", lang)
        )
    }

    override fun getQuizById(id: Int, lang: String): ApiResponse<QuizResponse> {
        val quiz = dao.findById(id)
        return ApiResponse(
            success = true,
            data = quiz?.convertToResponse(),
            message = Localization.get("quiz_retrieved_successfully", lang)
        )
    }

    override fun createQuiz(request: CreateQuizRequest, lang: String): ApiResponse<Int> {
        val id = dao.create(request.convertToDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("quiz_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("quiz_created_successfully", lang)
        )
    }

    override fun updateQuiz(id: Int, request: UpdateQuizRequest, lang: String): ApiResponse<Nothing> {
        val updated = dao.update(request.convertToDto(id))
        if (!updated) throw IllegalArgumentException(Localization.get("quiz_update_failed", lang))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("quiz_updated_successfully", lang)
        )
    }

    override fun deleteQuiz(id: Int, lang: String): ApiResponse<Nothing> {

        val deleted = dao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("quiz_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("quiz_deleted_successfully", lang)
        )
    }
}