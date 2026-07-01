package com.fathersprophets.backend.services.escapeegyptanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegyptanswer.CreateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.EscapeEgyptAnswerResponse
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerStatusRequest

interface IEscapeEgyptAnswerService {
    fun getAllAnswers(lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun getAnswerById(id: Int?, lang: String): ApiResponse<EscapeEgyptAnswerResponse>
    fun getAnswersByEscapeEgyptId(escapeEgyptId: Int?, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun getAnswersByQuestionId(questionId: Int?, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun getAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun createAnswer(request: CreateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse>
    fun updateAnswer(id: Int?, request: UpdateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse>
    fun updateAnswerStatus(id: Int?, request: UpdateEscapeEgyptAnswerStatusRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse>
    fun deleteAnswer(id: Int?, lang: String): ApiResponse<Nothing>
}