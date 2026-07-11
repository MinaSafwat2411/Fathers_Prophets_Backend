package com.fathersprophets.backend.database.repository.quizdayquestion

import com.fathersprophets.backend.database.dao.quiz.QuizDayQuestionDao
import com.fathersprophets.backend.database.tables.McqCorrectAnswer
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.QuizDayQuestionDto
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

    override fun createQuestion(request: CreateQuizDayQuestionRequest, lang: String): ApiResponse<QuizDayQuestionResponse> {
        val id = dao.create(request.convertToDto())
        val created = dao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("quiz_day_question_created_successfully", lang)
        )
    }

    override fun createQuestions(requests: List<CreateQuizDayQuestionRequest>, lang: String): ApiResponse<List<QuizDayQuestionResponse>> {
        val created = dao.createMany(requests.map { it.convertToDto() })
        return ApiResponse(
            success = true,
            data = created.map { it.convertToResponse() },
            message = Localization.get("quiz_day_questions_created_successfully", lang)
        )
    }

    override fun updateQuestion(id: Int, request: UpdateQuizDayQuestionRequest, lang: String): ApiResponse<QuizDayQuestionResponse> {
        dao.update(request.convertToDto(id))
        val updated = dao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("quiz_day_question_updated_successfully", lang)
        )
    }

    override fun deleteQuestion(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("quiz_day_question_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = QuizDayQuestionDto(
        id = id,
        quizDayId = 0,
        question = "",
        choice1 = "",
        choice2 = "",
        choice3 = null,
        choice4 = null,
        correctAnswer = McqCorrectAnswer.`1`
    )
}