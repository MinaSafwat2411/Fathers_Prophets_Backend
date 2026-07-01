package com.fathersprophets.backend.models.matchingpair

import com.fathersprophets.backend.models.dto.MatchingPairDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateMatchingPairRequest(
    val personId: Int,
    val personName: String,
    val otherSide: String
) {
    fun convertToDto() = MatchingPairDto(
        id = 0,
        personId = personId,
        personName = personName,
        otherSide = otherSide
    )
}