package com.fathersprophets.backend.modules.matchingpair

import kotlinx.serialization.Serializable

@Serializable
data class MatchingPairDto(
    val id: Int,
    val title: String
)

@Serializable
data class MatchingPairCreateDto(
    val title: String
)

@Serializable
data class MatchingPairUpdateDto(
    val title: String? = null
)