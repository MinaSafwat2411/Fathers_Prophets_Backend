package com.fathersprophets.backend.database.repository.activity.matchingpair

import com.fathersprophets.backend.database.dao.activity.matchpair.MatchingPairDao
import com.fathersprophets.backend.models.ApiResponse
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

    override fun createPair(request: CreateMatchingPairRequest, lang: String): ApiResponse<Int> {
        val id = dao.create(request.convertToDto())

        if (id == 0) throw IllegalArgumentException(Localization.get("matching_pair_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("matching_pair_created_successfully", lang)
        )
    }

    override fun updatePair(id: Int, request: UpdateMatchingPairRequest, lang: String): ApiResponse<Nothing> {
        val  update = dao.update(request.convertToDto(id))

        if (!update) throw IllegalArgumentException(Localization.get("matching_pair_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("matching_pair_updated_successfully", lang)
        )
    }

    override fun deletePair(id: Int, lang: String): ApiResponse<Nothing> {
        val delete = dao.delete(id)

        if (!delete) throw IllegalArgumentException(Localization.get("matching_pair_delete_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("matching_pair_deleted_successfully", lang)
        )
    }
}