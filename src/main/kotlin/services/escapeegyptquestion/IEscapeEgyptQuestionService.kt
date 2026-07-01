package com.fathersprophets.backend.services.escapeegyptquestion

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegyptquestion.CreateEscapeEgyptQuestionRequest
import com.fathersprophets.backend.models.escapeegyptquestion.EscapeEgyptQuestionResponse
import com.fathersprophets.backend.models.escapeegyptquestion.UpdateEscapeEgyptQuestionRequest

interface IEscapeEgyptQuestionService {
    fun getAllQuestions(lang: String): ApiResponse<List<EscapeEgyptQuestionResponse>>
    fun getQuestionById(id: Int?, lang: String): ApiResponse<EscapeEgyptQuestionResponse>
    fun getQuestionsByEscapeEgyptId(escapeEgyptId: Int?, lang: String): ApiResponse<List<EscapeEgyptQuestionResponse>>
    fun createQuestion(request: CreateEscapeEgyptQuestionRequest, lang: String): ApiResponse<EscapeEgyptQuestionResponse>
    fun updateQuestion(id: Int?, request: UpdateEscapeEgyptQuestionRequest, lang: String): ApiResponse<EscapeEgyptQuestionResponse>
    fun deleteQuestion(id: Int?, lang: String): ApiResponse<Nothing>
}