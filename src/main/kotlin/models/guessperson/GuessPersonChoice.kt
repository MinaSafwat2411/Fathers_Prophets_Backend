package com.fathersprophets.backend.models.guessperson

import kotlinx.serialization.Serializable

@Serializable
data class GuessPersonChoice(
    val personId: Int,
    val personName: String
)