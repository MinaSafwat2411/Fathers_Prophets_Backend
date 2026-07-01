package com.fathersprophets.backend.database.repository.matchingpair

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.matchingpair.CreateMatchingPairRequest
import com.fathersprophets.backend.models.matchingpair.MatchingPairResponse
import com.fathersprophets.backend.models.matchingpair.UpdateMatchingPairRequest

interface IMatchingPairRepository {
    fun getAllPairs(lang: String): ApiResponse<List<MatchingPairResponse>>
    fun getPairById(id: Int, lang: String): ApiResponse<MatchingPairResponse>
    fun createPair(request: CreateMatchingPairRequest, lang: String): ApiResponse<MatchingPairResponse>
    fun updatePair(id: Int, request: UpdateMatchingPairRequest, lang: String): ApiResponse<MatchingPairResponse>
    fun deletePair(id: Int, lang: String): ApiResponse<Nothing>
}