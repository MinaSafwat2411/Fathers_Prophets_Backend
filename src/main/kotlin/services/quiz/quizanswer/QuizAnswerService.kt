package com.fathersprophets.backend.services.quiz.quizanswer

import com.fathersprophets.backend.database.repository.quiz.quizanswer.IQuizAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizanswer.CreateQuizAnswerRequest
import com.fathersprophets.backend.models.quizanswer.QuizAnswerResponse
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class QuizAnswerService(
    private val repository: IQuizAnswerRepository
) : IQuizAnswerService {

    override fun getAllQuizAnswers(lang: String): ApiResponse<List<QuizAnswerResponse>> {
        return repository.getAllQuizAnswers(lang)
    }

    override fun getQuizAnswersByUserIdAndDayId(userId: Int?, dayId: Int?, lang: String): ApiResponse<List<QuizAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        if (dayId == null) throw IllegalArgumentException(Localization.get("quiz_day_id_required", lang))
        return repository.getQuizAnswersByUserIdAndDayId(userId, dayId, lang)
    }

    override fun createQuizAnswer(request: CreateQuizAnswerRequest, lang: String): ApiResponse<QuizAnswerResponse> {
        validateCreateRequest(request, lang)
        return repository.createQuizAnswer(request, lang)
    }

    override fun createQuizAnswers(requests: List<CreateQuizAnswerRequest>, lang: String): ApiResponse<List<QuizAnswerResponse>> {
        if (requests.isEmpty()) throw IllegalArgumentException(Localization.get("quiz_answers_required", lang))
        requests.forEach { validateCreateRequest(it, lang) }
        return repository.createQuizAnswers(requests, lang)
    }

    override fun deleteQuizAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("quiz_answer_id_required", lang))
        return repository.deleteQuizAnswer(id, lang)
    }

    private fun validateCreateRequest(request: CreateQuizAnswerRequest, lang: String) {
        validateRequired(
            request.quizId to "quizId",
            request.questionId to "questionId",
            request.dayId to "dayId",
            request.userId to "userId",
            request.answer to "answer",
            lang = lang
        )
    }
}