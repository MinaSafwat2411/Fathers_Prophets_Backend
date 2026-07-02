package com.fathersprophets.backend.database.repository.quizdayquestion

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizdayquestion.CreateQuizDayQuestionRequest
import com.fathersprophets.backend.models.quizdayquestion.QuizDayQuestionResponse
import com.fathersprophets.backend.models.quizdayquestion.UpdateQuizDayQuestionRequest

interface IQuizDayQuestionRepository {
    fun getAllQuestions(lang: String): ApiResponse<List<QuizDayQuestionResponse>>
    fun getQuestionById(id: Int, lang: String): ApiResponse<QuizDayQuestionResponse>
    fun getQuestionsByQuizDayId(quizDayId: Int, lang: String): ApiResponse<List<QuizDayQuestionResponse>>
    fun createQuestion(request: CreateQuizDayQuestionRequest, lang: String): ApiResponse<QuizDayQuestionResponse>
    fun createQuestions(requests: List<CreateQuizDayQuestionRequest>, lang: String): ApiResponse<List<QuizDayQuestionResponse>>
    fun updateQuestion(id: Int, request: UpdateQuizDayQuestionRequest, lang: String): ApiResponse<QuizDayQuestionResponse>
    fun deleteQuestion(id: Int, lang: String): ApiResponse<Nothing>
}