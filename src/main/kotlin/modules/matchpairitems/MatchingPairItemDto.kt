package com.fathersprophets.backend.modules.matchpairitems

import kotlinx.serialization.Serializable

@Serializable
data class MatchingPairItemDto(
    val id: Int,
    val pairId: Int,
    val right: String,
    val left: String
)

@Serializable
data class MatchingPairItemCreateDto(
    val pairId: Int,
    val right: String,
    val left: String
)

@Serializable
data class MatchingPairItemUpdateDto(
    val pairId: Int? = null,
    val right: String? = null,
    val left: String? = null
)