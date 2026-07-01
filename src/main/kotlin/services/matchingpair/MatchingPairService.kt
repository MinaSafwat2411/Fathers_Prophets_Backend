package com.fathersprophets.backend.services.matchingpair

import com.fathersprophets.backend.database.repository.matchingpair.IMatchingPairRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.matchingpair.CreateMatchingPairRequest
import com.fathersprophets.backend.models.matchingpair.MatchingPairResponse
import com.fathersprophets.backend.models.matchingpair.UpdateMatchingPairRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class MatchingPairService(
    private val repository: IMatchingPairRepository
) : IMatchingPairService {

    override fun getAllPairs(lang: String): ApiResponse<List<MatchingPairResponse>> {
        return repository.getAllPairs(lang)
    }

    override fun getPairById(id: Int?, lang: String): ApiResponse<MatchingPairResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("matching_pair_id_required", lang))
        return repository.getPairById(id, lang)
    }

    override fun createPair(request: CreateMatchingPairRequest, lang: String): ApiResponse<MatchingPairResponse> {
        validateRequired(
            request.personId to "personId",
            request.personName to "personName",
            request.otherSide to "otherSide",
            lang = lang
        )
        return repository.createPair(request, lang)
    }

    override fun updatePair(id: Int?, request: UpdateMatchingPairRequest, lang: String): ApiResponse<MatchingPairResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("matching_pair_id_required", lang))
        validateRequired(
            request.personId to "personId",
            request.personName to "personName",
            request.otherSide to "otherSide",
            lang = lang
        )
        return repository.updatePair(id, request, lang)
    }

    override fun deletePair(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("matching_pair_id_required", lang))
        return repository.deletePair(id, lang)
    }
}