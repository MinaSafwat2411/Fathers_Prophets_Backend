package com.fathersprophets.backend.database.repository.activity.escapeegypt

import com.fathersprophets.backend.database.dao.activity.escapeegypt.EscapeEgyptDao
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

    override fun getEscapeEgyptById(id: Int, lang: String): ApiResponse<EscapeEgyptResponse> {
        val item = dao.findById(id)
        return ApiResponse(
            success = true,
            data = item?.convertToResponse(),
            message = Localization.get("escape_egypt_retrieved_successfully", lang)
        )
    }

    override fun createEscapeEgypt(request: CreateEscapeEgyptRequest, lang: String): ApiResponse<Int> {
        val id = dao.create(request.convertToDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("escape_egypt_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("escape_egypt_created_successfully", lang)
        )
    }

    override fun updateEscapeEgypt(id: Int, request: UpdateEscapeEgyptRequest, lang: String): ApiResponse<Nothing> {
        val updated = dao.update(request.convertToDto(id))

        if (!updated) throw IllegalArgumentException(Localization.get("escape_egypt_not_found", lang))

        return ApiResponse(
            success = true,
            data = null,
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