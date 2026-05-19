package com.fathersprophets.backend.models.event

import kotlinx.serialization.Serializable

@Serializable
data class EventCountsResponse(
    val total : Int,
    val football : Int,
    val volleyball : Int,
    val chess : Int,
    val pingPong : Int,
    val pray : Int,
    val praise : Int,
    val doctrine : Int,
    val bible : Int,
    val ritual : Int,
    val coptic : Int,
    val choir : Int,
    val mahrgan : Int,
    val odas : Int,
    val shmas : Int,
    val melodies : Int,
)
