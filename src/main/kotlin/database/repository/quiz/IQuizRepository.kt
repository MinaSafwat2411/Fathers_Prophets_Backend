package com.fathersprophets.backend.database.repository.quiz

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quiz.CreateQuizRequest
import com.fathersprophets.backend.models.quiz.QuizResponse
import com.fathersprophets.backend.models.quiz.UpdateQuizRequest

interface IQuizRepository {
    fun getAllQuizzes(lang: String): ApiResponse<List<QuizResponse>>
    fun getQuizById(id: Int, lang: String): ApiResponse<QuizResponse>
    fun createQuiz(request: CreateQuizRequest, lang: String): ApiResponse<Int>
    fun updateQuiz(id: Int, request: UpdateQuizRequest, lang: String): ApiResponse<Nothing>
    fun deleteQuiz(id: Int, lang: String): ApiResponse<Nothing>
}