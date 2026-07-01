package com.fathersprophets.backend.models.matchingpair

import com.fathersprophets.backend.models.dto.MatchingPairDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateMatchingPairRequest(
    val personId: Int,
    val personName: String,
    val otherSide: String
) {
    fun convertToDto(id: Int) = MatchingPairDto(
        id = id,
        personId = personId,
        personName = personName,
        otherSide = otherSide
    )
}