package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.matchingpairanswer.MatchingPairAnswerResponse

data class MatchingPairAnswerDto(
    val id: Int,
    val pairId: Int,
    val userId: Int,
    val userPair: Map<Int, String>,
    val status: AnswerStatus
) {
    fun convertToResponse() = MatchingPairAnswerResponse(
        id = id,
        pairId = pairId,
        userId = userId,
        userPair = userPair,
        status = status.name
    )
}