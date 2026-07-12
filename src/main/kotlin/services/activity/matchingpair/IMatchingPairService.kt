package com.fathersprophets.backend.services.activity.matchingpair

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.matchingpair.CreateMatchingPairRequest
import com.fathersprophets.backend.models.matchingpair.MatchingPairResponse
import com.fathersprophets.backend.models.matchingpair.UpdateMatchingPairRequest

interface IMatchingPairService {
    fun getAllPairs(lang: String): ApiResponse<List<MatchingPairResponse>>
    fun getPairById(id: Int?, lang: String): ApiResponse<MatchingPairResponse>
    fun createPair(request: CreateMatchingPairRequest, lang: String): ApiResponse<Int>
    fun updatePair(id: Int?, request: UpdateMatchingPairRequest, lang: String): ApiResponse<Nothing>
    fun deletePair(id: Int?, lang: String): ApiResponse<Nothing>
}