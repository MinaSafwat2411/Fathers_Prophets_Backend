package com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegyptanswer.CreateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.EscapeEgyptAnswerResponse
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerRequest
import com.fathersprophets.backend.models.escapeegyptanswer.UpdateEscapeEgyptAnswerStatusRequest

interface IEscapeEgyptAnswerRepository {
    fun getAllAnswers(lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun getAnswerById(id: Int, lang: String): ApiResponse<EscapeEgyptAnswerResponse>
    fun getAnswersByEscapeEgyptId(escapeEgyptId: Int, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun getAnswersByQuestionId(questionId: Int, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun getAnswersByUserId(userId: Int, lang: String): ApiResponse<List<EscapeEgyptAnswerResponse>>
    fun createAnswer(request: CreateEscapeEgyptAnswerRequest, lang: String): ApiResponse<Int>
    fun updateAnswer(id: Int, request: UpdateEscapeEgyptAnswerRequest, lang: String): ApiResponse<Nothing>
    fun updateAnswerStatus(id: Int, request: UpdateEscapeEgyptAnswerStatusRequest, lang: String): ApiResponse<Nothing>
    fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing>
}