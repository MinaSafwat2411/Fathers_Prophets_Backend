package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.matchingpair.MatchingPairResponse

data class MatchingPairDto(
    val id: Int,
    val personId: Int,
    val personName: String,
    val otherSide: String
) {
    fun convertToResponse() = MatchingPairResponse(
        id = id,
        personId = personId,
        personName = personName,
        otherSide = otherSide
    )
}