package com.fathersprophets.backend.models.matchingpair

import kotlinx.serialization.Serializable

@Serializable
data class MatchingPairResponse(
    val id: Int,
    val personId: Int,
    val personName: String,
    val otherSide: String
)