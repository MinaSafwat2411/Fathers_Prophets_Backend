package com.fathersprophets.backend.services.quiz

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quiz.CreateQuizRequest
import com.fathersprophets.backend.models.quiz.QuizResponse
import com.fathersprophets.backend.models.quiz.UpdateQuizRequest

interface IQuizService {
    fun getAllQuizzes(lang: String): ApiResponse<List<QuizResponse>>
    fun getQuizById(id: Int?, lang: String): ApiResponse<QuizResponse>
    fun createQuiz(request: CreateQuizRequest, lang: String): ApiResponse<QuizResponse>
    fun updateQuiz(id: Int?, request: UpdateQuizRequest, lang: String): ApiResponse<QuizResponse>
    fun deleteQuiz(id: Int?, lang: String): ApiResponse<Nothing>
}