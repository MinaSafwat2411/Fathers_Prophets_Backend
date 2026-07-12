package com.fathersprophets.backend.database.repository.activity.guessperson.guesspersonanswer

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
    fun createAnswer(request: CreateGuessPersonAnswerRequest, lang: String): ApiResponse<Int>
    fun updateAnswer(id: Int, request: UpdateGuessPersonAnswerRequest, lang: String): ApiResponse<Nothing>
    fun updateAnswerStatus(id: Int, request: UpdateGuessPersonAnswerStatusRequest, lang: String): ApiResponse<Nothing>
    fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing>
}
