package com.fathersprophets.backend.models.matchingpairanswer

import kotlinx.serialization.Serializable

@Serializable
data class MatchingPairAnswerResponse(
    val id: Int,
    val pairId: Int,
    val userId: Int,
    val userPair: Map<Int, String>,
    val status: String
)