package com.fathersprophets.backend.services.quizday

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizday.CreateQuizDayRequest
import com.fathersprophets.backend.models.quizday.QuizDayResponse
import com.fathersprophets.backend.models.quizday.UpdateQuizDayRequest

interface IQuizDayService {
    fun getAllQuizDays(lang: String): ApiResponse<List<QuizDayResponse>>
    fun getQuizDayById(id: Int?, lang: String): ApiResponse<QuizDayResponse>
    fun getQuizDaysByQuizId(quizId: Int?, lang: String): ApiResponse<List<QuizDayResponse>>
    fun createQuizDay(request: CreateQuizDayRequest, lang: String): ApiResponse<QuizDayResponse>
    fun updateQuizDay(id: Int?, request: UpdateQuizDayRequest, lang: String): ApiResponse<QuizDayResponse>
    fun deleteQuizDay(id: Int?, lang: String): ApiResponse<Nothing>
}