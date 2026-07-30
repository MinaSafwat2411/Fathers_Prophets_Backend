package com.fathersprophets.backend.database.repository.activity.escapeegypt

import com.fathersprophets.backend.database.dao.EscapeEgyptDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.escapeegypt.CreateEscapeEgyptRequest
import com.fathersprophets.backend.models.escapeegypt.EscapeEgyptResponse
import com.fathersprophets.backend.models.escapeegypt.UpdateEscapeEgyptRequest
import com.fathersprophets.backend.utils.Localization

class EscapeEgyptRepository(
    private val dao: EscapeEgyptDao
) : IEscapeEgyptRepository {

    override fun getAllEscapeEgypt(lang: String): ApiResponse<List<EscapeEgyptResponse>> {
        val items = dao.findAll()
        return ApiResponse(
            success = true,
            data = items.map { it.convertToResponse() },
            message = Localization.get("escape_egypts_retrieved_successfully", lang)
        )
    }

    override fun createEscapeEgypt(request: CreateEscapeEgyptRequest, lang: String): ApiResponse<EscapeEgyptResponse> {
        val created = dao.create(request.convertToDto())
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = created.convertToResponse(),
            message = Localization.get("escape_egypt_created_successfully", lang)
        )
    }

    override fun updateEscapeEgypt(id: Int, request: UpdateEscapeEgyptRequest, lang: String): ApiResponse<EscapeEgyptResponse> {
        val updated = dao.update(request.convertToDto(id))
            ?: throw IllegalArgumentException(Localization.get("escape_egypt_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
            message = Localization.get("escape_egypt_updated_successfully", lang)
        )
    }

    override fun deleteEscapeEgypt(id: Int, lang: String): ApiResponse<Nothing> {
        val deleted = dao.delete(id)

        if (!deleted) throw IllegalArgumentException(Localization.get("escape_egypt_not_found", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("escape_egypt_deleted_successfully", lang)
        )
    }
}