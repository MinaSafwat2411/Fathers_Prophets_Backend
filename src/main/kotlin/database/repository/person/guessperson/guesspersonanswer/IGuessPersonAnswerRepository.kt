package com.fathersprophets.backend.database.repository.person.guessperson.guesspersonanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.guesspersonanswer.CreateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.GuessPersonAnswerResponse
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerStatusRequest

interface IGuessPersonAnswerRepository {
    fun getAllAnswers(lang: String): ApiResponse<List<GuessPersonAnswerResponse>>
    fun getAnswersByUserIdAndQuestionId(userId: Int, questionId: Int,lang: String): ApiResponse<List<GuessPersonAnswerResponse>>
    fun createAnswer(request: CreateGuessPersonAnswerRequest, lang: String): ApiResponse<GuessPersonAnswerResponse>
    fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing>
}
