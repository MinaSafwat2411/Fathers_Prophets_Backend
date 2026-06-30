package com.fathersprophets.backend.database.repository.guessperson

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.guessperson.CreateGuessPersonQuestionRequest
import com.fathersprophets.backend.models.guessperson.GuessPersonQuestionResponse
import com.fathersprophets.backend.models.guessperson.UpdateGuessPersonQuestionRequest

interface IGuessPersonQuestionRepository {
    fun getAllQuestions(lang: String): ApiResponse<List<GuessPersonQuestionResponse>>
    fun getQuestionById(id: Int, lang: String): ApiResponse<GuessPersonQuestionResponse>
    fun createQuestion(request: CreateGuessPersonQuestionRequest, lang: String): ApiResponse<GuessPersonQuestionResponse>
    fun updateQuestion(id: Int, request: UpdateGuessPersonQuestionRequest, lang: String): ApiResponse<GuessPersonQuestionResponse>
    fun deleteQuestion(id: Int, lang: String): ApiResponse<Nothing>
}