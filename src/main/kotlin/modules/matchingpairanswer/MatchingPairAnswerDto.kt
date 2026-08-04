package com.fathersprophets.backend.modules.matchingpairanswer

import com.fathersprophets.backend.database.enums.AnswerStatus
import kotlinx.serialization.Serializable

@Serializable
data class MatchingPairAnswerDto(
    val id: Int,
    val pairId: Int,
    val userId: Int,
    val right: String,
    val left: String,
    val status: AnswerStatus
)

@Serializable
data class MatchingPairAnswerCreateDto(
    val pairId: Int,
    val userId: Int,
    val right: String,
    val left: String,
    val status: AnswerStatus
)

@Serializable
data class MatchingPairAnswerUpdateDto(
    val pairId: Int? = null,
    val userId: Int? = null,
    val right: String? = null,
    val left: String? = null,
    val status: AnswerStatus? = null
)