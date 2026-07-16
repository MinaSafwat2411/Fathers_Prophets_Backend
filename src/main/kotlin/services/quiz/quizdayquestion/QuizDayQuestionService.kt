package com.fathersprophets.backend.services.quiz.quizdayquestion

import com.fathersprophets.backend.database.repository.quiz.quizdayquestion.IQuizDayQuestionRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizdayquestion.CreateQuizDayQuestionRequest
import com.fathersprophets.backend.models.quizdayquestion.QuizDayQuestionResponse
import com.fathersprophets.backend.models.quizdayquestion.UpdateQuizDayQuestionRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class QuizDayQuestionService(
    private val repository: IQuizDayQuestionRepository
) : IQuizDayQuestionService {

    override fun getAllQuestions(lang: String): ApiResponse<List<QuizDayQuestionResponse>> {
        return repository.getAllQuestions(lang)
    }

    override fun getQuestionsByQuizDayId(quizDayId: Int?, lang: String): ApiResponse<List<QuizDayQuestionResponse>> {
        if (quizDayId == null) throw IllegalArgumentException(Localization.get("quiz_day_id_required", lang))
        return repository.getQuestionsByQuizDayId(quizDayId, lang)
    }

    override fun createQuestion(request: CreateQuizDayQuestionRequest, lang: String): ApiResponse<Int> {
        validateCreateRequest(request, lang)
        return repository.createQuestion(request, lang)
    }

    override fun createQuestions(requests: List<CreateQuizDayQuestionRequest>, lang: String): ApiResponse<List<Int>> {
        if (requests.isEmpty()) throw IllegalArgumentException(Localization.get("quiz_day_questions_required", lang))
        requests.forEach { validateCreateRequest(it, lang) }
        return repository.createQuestions(requests, lang)
    }

    override fun updateQuestion(id: Int?, request: UpdateQuizDayQuestionRequest, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("quiz_day_question_id_required", lang))
        validateRequired(
            request.quizDayId to "quizDayId",
            request.question to "question",
            request.choice1 to "choice1",
            request.choice2 to "choice2",
            request.correctAnswer to "correctAnswer",
            lang = lang
        )
        return repository.updateQuestion(id, request, lang)
    }

    override fun deleteQuestion(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("quiz_day_question_id_required", lang))
        return repository.deleteQuestion(id, lang)
    }

    private fun validateCreateRequest(request: CreateQuizDayQuestionRequest, lang: String) {
        validateRequired(
            request.quizDayId to "quizDayId",
            request.question to "question",
            request.choice1 to "choice1",
            request.choice2 to "choice2",
            request.correctAnswer to "correctAnswer",
            lang = lang
        )
    }
}