package com.fathersprophets.backend.services.activity.matchingpair.matchingpairanswer

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.matchingpairanswer.CreateMatchingPairAnswerRequest
import com.fathersprophets.backend.models.matchingpairanswer.MatchingPairAnswerResponse
import com.fathersprophets.backend.models.matchingpairanswer.UpdateMatchingPairAnswerRequest

interface IMatchingPairAnswerService {
    fun getAllAnswers(lang: String): ApiResponse<List<MatchingPairAnswerResponse>>
    fun getAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<MatchingPairAnswerResponse>>
    fun createAnswer(request: CreateMatchingPairAnswerRequest, lang: String): ApiResponse<MatchingPairAnswerResponse>
    fun deleteAnswer(id: Int?, lang: String): ApiResponse<Nothing>
}