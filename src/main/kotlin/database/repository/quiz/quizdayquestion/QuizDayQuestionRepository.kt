package com.fathersprophets.backend.database.repository.quiz.quizdayquestion

import com.fathersprophets.backend.database.dao.quiz.QuizDayQuestionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizdayquestion.CreateQuizDayQuestionRequest
import com.fathersprophets.backend.models.quizdayquestion.QuizDayQuestionResponse
import com.fathersprophets.backend.models.quizdayquestion.UpdateQuizDayQuestionRequest
import com.fathersprophets.backend.utils.Localization

class QuizDayQuestionRepository(
    private val dao: QuizDayQuestionDao
) : IQuizDayQuestionRepository {

    override fun getAllQuestions(lang: String): ApiResponse<List<QuizDayQuestionResponse>> {
        val questions = dao.findAll()
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToResponse() },
            message = Localization.get("quiz_day_questions_retrieved_successfully", lang)
        )
    }

    override fun getQuestionById(id: Int, lang: String): ApiResponse<QuizDayQuestionResponse> {
        val question = dao.findById(id)
        return ApiResponse(
            success = true,
            data = question?.convertToResponse(),
            message = Localization.get("quiz_day_question_retrieved_successfully", lang)
        )
    }

    override fun getQuestionsByQuizDayId(quizDayId: Int, lang: String): ApiResponse<List<QuizDayQuestionResponse>> {
        val questions = dao.findByQuizDayId(quizDayId)
        return ApiResponse(
            success = true,
            data = questions.map { it.convertToResponse() },
            message = Localization.get("quiz_day_questions_retrieved_successfully", lang)
        )
    }

    override fun createQuestion(request: CreateQuizDayQuestionRequest, lang: String): ApiResponse<Int> {
        val id = dao.create(request.convertToDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("quiz_day_question_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("quiz_day_question_created_successfully", lang)
        )
    }

    override fun createQuestions(requests: List<CreateQuizDayQuestionRequest>, lang: String): ApiResponse<List<Int>> {
        val created = dao.createMany(requests.map { it.convertToDto() })
        if (created.size != requests.size) throw IllegalArgumentException(Localization.get("quiz_day_questions_creation_failed", lang))
        return ApiResponse(
            success = true,
            data = created.map { it.id },
            message = Localization.get("quiz_day_questions_created_successfully", lang)
        )
    }

    override fun updateQuestion(id: Int, request: UpdateQuizDayQuestionRequest, lang: String): ApiResponse<Nothing> {

        val  update = dao.update(request.convertToDto(id))

        if (!update) throw IllegalArgumentException(Localization.get("quiz_day_question_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("quiz_day_question_updated_successfully", lang)
        )
    }

    override fun deleteQuestion(id: Int, lang: String): ApiResponse<Nothing> {

        val deleted = dao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("quiz_day_question_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("quiz_day_question_deleted_successfully", lang)
        )
    }
}