package com.fathersprophets.backend.models.dto

import com.fathersprophets.backend.models.event.EventCountsResponse

data class EventCountsDto(
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
){
    fun convertToEventCountResponse() = EventCountsResponse(
        total = total,
        football = football,
        volleyball = volleyball,
        chess = chess,
        pingPong = pingPong,
        pray = pray,
        praise = praise,
        doctrine = doctrine,
        bible = bible,
        ritual = ritual,
        coptic = coptic,
        choir = choir,
        mahrgan = mahrgan,
        odas = odas,
        shmas = shmas,
        melodies = melodies
    )
}
