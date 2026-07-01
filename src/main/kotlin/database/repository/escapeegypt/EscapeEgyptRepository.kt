package com.fathersprophets.backend.database.repository.escapeegypt

import com.fathersprophets.backend.database.dao.EscapeEgyptDao
import com.fathersprophets.backend.database.tables.EscapeEgyptType
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.EscapeEgyptDto
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

    override fun createEscapeEgypt(request: CreateEscapeEgyptRequest, lang: String): ApiResponse<EscapeEgyptResponse> {
        val id = dao.create(request.convertToDto())
        val created = dao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("escape_egypt_created_successfully", lang)
        )
    }

    override fun updateEscapeEgypt(id: Int, request: UpdateEscapeEgyptRequest, lang: String): ApiResponse<EscapeEgyptResponse> {
        dao.update(request.convertToDto(id))
        val updated = dao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("escape_egypt_updated_successfully", lang)
        )
    }

    override fun deleteEscapeEgypt(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("escape_egypt_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = EscapeEgyptDto(
        id = id,
        title = "",
        type = EscapeEgyptType.from
    )
}