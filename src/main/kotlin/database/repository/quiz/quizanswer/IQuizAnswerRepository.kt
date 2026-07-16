package com.fathersprophets.backend.database.repository.quiz.quizanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quizanswer.CreateQuizAnswerRequest
import com.fathersprophets.backend.models.quizanswer.QuizAnswerResponse
import com.fathersprophets.backend.models.quizanswer.UpdateQuizAnswerRequest

interface IQuizAnswerRepository {
    fun getAllQuizAnswers(lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun getQuizAnswersByUserIdAndDayId(dayId: Int, userId: Int ,lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun createQuizAnswer(request: CreateQuizAnswerRequest, lang: String): ApiResponse<QuizAnswerResponse>
    fun createQuizAnswers(requests: List<CreateQuizAnswerRequest>, lang: String): ApiResponse<List<QuizAnswerResponse>>
    fun deleteQuizAnswer(id: Int, lang: String): ApiResponse<Nothing>
}