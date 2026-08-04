package com.fathersprophets.backend.models.matchingpairanswer

import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.dto.MatchingPairAnswerDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateMatchingPairAnswerRequest(
    val pairId: Int,
    val userId: Int,
    val userPair: Map<Int, String>
){
    fun convertToDto(id : Int) = MatchingPairAnswerDto(
        id = id,
        pairId = pairId,
        userId = userId,
        userPair = userPair,
        status = AnswerStatus.TEACHER_STILL_NOT_CORRECTED
    )
}