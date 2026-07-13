package com.fathersprophets.backend.database.repository.activity.escapeegypt

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegypt.CreateEscapeEgyptRequest
import com.fathersprophets.backend.models.escapeegypt.EscapeEgyptResponse
import com.fathersprophets.backend.models.escapeegypt.UpdateEscapeEgyptRequest

interface IEscapeEgyptRepository {
    fun getAllEscapeEgypt(lang: String): ApiResponse<List<EscapeEgyptResponse>>
    fun createEscapeEgypt(request: CreateEscapeEgyptRequest, lang: String): ApiResponse<EscapeEgyptResponse>
    fun updateEscapeEgypt(id: Int, request: UpdateEscapeEgyptRequest, lang: String): ApiResponse<EscapeEgyptResponse>
    fun deleteEscapeEgypt(id: Int, lang: String): ApiResponse<Nothing>
}