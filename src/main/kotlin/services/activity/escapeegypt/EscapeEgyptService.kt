package com.fathersprophets.backend.services.activity.escapeegypt

import com.fathersprophets.backend.database.repository.activity.escapeegypt.IEscapeEgyptRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegypt.CreateEscapeEgyptRequest
import com.fathersprophets.backend.models.escapeegypt.EscapeEgyptResponse
import com.fathersprophets.backend.models.escapeegypt.UpdateEscapeEgyptRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class EscapeEgyptService(
    private val repository: IEscapeEgyptRepository
) : IEscapeEgyptService {

    override fun getAllEscapeEgypt(lang: String): ApiResponse<List<EscapeEgyptResponse>> {
        return repository.getAllEscapeEgypt(lang)
    }


    override fun createEscapeEgypt(request: CreateEscapeEgyptRequest, lang: String): ApiResponse<EscapeEgyptResponse> {
        validateRequired(
            request.title to "title",
            request.type to "type",
            lang = lang
        )
        return repository.createEscapeEgypt(request, lang)
    }

    override fun updateEscapeEgypt(id: Int?, request: UpdateEscapeEgyptRequest, lang: String): ApiResponse<EscapeEgyptResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("escape_egypt_id_required", lang))
        validateRequired(
            request.title to "title",
            request.type to "type",
            lang = lang
        )
        return repository.updateEscapeEgypt(id, request, lang)
    }

    override fun deleteEscapeEgypt(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("escape_egypt_id_required", lang))
        return repository.deleteEscapeEgypt(id, lang)
    }
}