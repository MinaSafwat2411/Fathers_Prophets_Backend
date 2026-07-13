package com.fathersprophets.backend.services.quiz.quizdayquestion

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizdayquestion.CreateQuizDayQuestionRequest
import com.fathersprophets.backend.models.quizdayquestion.QuizDayQuestionResponse
import com.fathersprophets.backend.models.quizdayquestion.UpdateQuizDayQuestionRequest

interface IQuizDayQuestionService {
    fun getAllQuestions(lang: String): ApiResponse<List<QuizDayQuestionResponse>>
    fun getQuestionById(id: Int?, lang: String): ApiResponse<QuizDayQuestionResponse>
    fun getQuestionsByQuizDayId(quizDayId: Int?, lang: String): ApiResponse<List<QuizDayQuestionResponse>>
    fun createQuestion(request: CreateQuizDayQuestionRequest, lang: String): ApiResponse<Int>
    fun createQuestions(requests: List<CreateQuizDayQuestionRequest>, lang: String): ApiResponse<List<Int>>
    fun updateQuestion(id: Int?, request: UpdateQuizDayQuestionRequest, lang: String): ApiResponse<Nothing>
    fun deleteQuestion(id: Int?, lang: String): ApiResponse<Nothing>
}