package com.fathersprophets.backend.models.matchingpairanswer

import kotlinx.serialization.Serializable

@Serializable
data class CreateMatchingPairAnswerRequest(
    val pairId: Int,
    val userId: Int,
    val userPair: Map<Int, String>
)