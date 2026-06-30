package com.fathersprophets.backend.database.repository.guesspersonanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.guesspersonanswer.CreateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.GuessPersonAnswerResponse
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerStatusRequest

interface IGuessPersonAnswerRepository {
    fun getAllAnswers(lang: String): ApiResponse<List<GuessPersonAnswerResponse>>
    fun getAnswerById(id: Int, lang: String): ApiResponse<GuessPersonAnswerResponse>
    fun getAnswersByQuestionId(questionId: Int, lang: String): ApiResponse<List<GuessPersonAnswerResponse>>
    fun getAnswersByUserId(userId: Int, lang: String): ApiResponse<List<GuessPersonAnswerResponse>>
    fun createAnswer(request: CreateGuessPersonAnswerRequest, lang: String): ApiResponse<GuessPersonAnswerResponse>
    fun updateAnswer(id: Int, request: UpdateGuessPersonAnswerRequest, lang: String): ApiResponse<GuessPersonAnswerResponse>
    fun updateAnswerStatus(id: Int, request: UpdateGuessPersonAnswerStatusRequest, lang: String): ApiResponse<GuessPersonAnswerResponse>
    fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing>
}
