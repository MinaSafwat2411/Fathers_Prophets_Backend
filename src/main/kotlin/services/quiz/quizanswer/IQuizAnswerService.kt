package com.fathersprophets.backend.services.quiz.quizanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizanswer.CreateQuizAnswerRequest
import com.fathersprophets.backend.models.quizanswer.QuizAnswerResponse
import com.fathersprophets.backend.models.quizanswer.UpdateQuizAnswerRequest

interface IQuizAnswerService {
    fun getAllQuizAnswers(lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun getQuizAnswersByUserIdAndDayId(userId: Int?, dayId: Int?, lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun createQuizAnswer(request: CreateQuizAnswerRequest, lang: String): ApiResponse<QuizAnswerResponse>
    fun createQuizAnswers(requests: List<CreateQuizAnswerRequest>, lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun deleteQuizAnswer(id: Int?, lang: String): ApiResponse<Nothing>
}