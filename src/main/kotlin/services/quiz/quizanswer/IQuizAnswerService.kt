package com.fathersprophets.backend.services.quiz.quizanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizanswer.CreateQuizAnswerRequest
import com.fathersprophets.backend.models.quizanswer.QuizAnswerResponse
import com.fathersprophets.backend.models.quizanswer.UpdateQuizAnswerRequest

interface IQuizAnswerService {
    fun getAllQuizAnswers(lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun getQuizAnswerById(id: Int?, lang: String): ApiResponse<QuizAnswerResponse>
    fun getQuizAnswersByQuestionId(questionId: Int?, lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun getQuizAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun getQuizAnswersByDayId(dayId: Int?, lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun getQuizAnswersByQuizId(quizId: Int?, lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun createQuizAnswer(request: CreateQuizAnswerRequest, lang: String): ApiResponse<Int>
    fun createQuizAnswers(requests: List<CreateQuizAnswerRequest>, lang: String): ApiResponse<List<Int>>
    fun updateQuizAnswer(id: Int?, request: UpdateQuizAnswerRequest, lang: String): ApiResponse<Nothing>
    fun deleteQuizAnswer(id: Int?, lang: String): ApiResponse<Nothing>
}