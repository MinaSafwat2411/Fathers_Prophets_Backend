package com.fathersprophets.backend.database.repository.matchingpair

import com.fathersprophets.backend.database.dao.MatchingPairDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.MatchingPairDto
import com.fathersprophets.backend.models.matchingpair.CreateMatchingPairRequest
import com.fathersprophets.backend.models.matchingpair.MatchingPairResponse
import com.fathersprophets.backend.models.matchingpair.UpdateMatchingPairRequest
import com.fathersprophets.backend.utils.Localization

class MatchingPairRepository(
    private val dao: MatchingPairDao
) : IMatchingPairRepository {

    override fun getAllPairs(lang: String): ApiResponse<List<MatchingPairResponse>> {
        val pairs = dao.findAll()
        return ApiResponse(
            success = true,
            data = pairs.map { it.convertToResponse() },
            message = Localization.get("matching_pairs_retrieved_successfully", lang)
        )
    }

    override fun getPairById(id: Int, lang: String): ApiResponse<MatchingPairResponse> {
        val pair = dao.findById(id)
        return ApiResponse(
            success = true,
            data = pair?.convertToResponse(),
            message = Localization.get("matching_pair_retrieved_successfully", lang)
        )
    }

    override fun createPair(request: CreateMatchingPairRequest, lang: String): ApiResponse<MatchingPairResponse> {
        val id = dao.create(request.convertToDto())
        val created = dao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("matching_pair_created_successfully", lang)
        )
    }

    override fun updatePair(id: Int, request: UpdateMatchingPairRequest, lang: String): ApiResponse<MatchingPairResponse> {
        dao.update(request.convertToDto(id))
        val updated = dao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("matching_pair_updated_successfully", lang)
        )
    }

    override fun deletePair(id: Int, lang: String): ApiResponse<Nothing> {
        dao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("matching_pair_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = MatchingPairDto(
        id = id,
        personId = 0,
        personName = "",
        otherSide = ""
    )
}