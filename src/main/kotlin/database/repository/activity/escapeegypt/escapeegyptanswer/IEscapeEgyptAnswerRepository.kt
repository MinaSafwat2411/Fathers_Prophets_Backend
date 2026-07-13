package com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegyptanswer.CreateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.EscapeEgyptAnswerResponse
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerStatusRequest

interface IEscapeEgyptAnswerRepository {
    fun getAllAnswers(lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun getAnswersByEscapeEgyptId(escapeEgyptId: Int, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun getAnswersByUserIdAndEscapeEgyptId(userId: Int, escapeEgyptId: Int, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun createAnswer(request: CreateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse>
    fun updateAnswer(id: Int, request: UpdateEscapeEgyptAnswerRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse>
    fun updateAnswerStatus(id: Int, request: UpdateEscapeEgyptAnswerStatusRequest, lang: String): ApiResponse<EscapeEgyptAnswerResponse>
    fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing>
}