package com.fathersprophets.backend.services.activity.matchingpair.matchingpairanswer

import com.fathersprophets.backend.database.repository.activity.matchingpair.matchingpairanswer.IMatchingPairAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.matchingpairanswer.CreateMatchingPairAnswerRequest
import com.fathersprophets.backend.models.matchingpairanswer.MatchingPairAnswerResponse
import com.fathersprophets.backend.models.matchingpairanswer.UpdateMatchingPairAnswerRequest
import com.fathersprophets.backend.utils.Localization

class MatchingPairAnswerService(
    private val repository: IMatchingPairAnswerRepository
) : IMatchingPairAnswerService {

    override fun getAllAnswers(lang: String): ApiResponse<List<MatchingPairAnswerResponse>> {
        return repository.getAllAnswers(lang)
    }

    override fun getAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<MatchingPairAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return repository.getAnswersByUserId(userId, lang)
    }

    override fun createAnswer(request: CreateMatchingPairAnswerRequest, lang: String): ApiResponse<MatchingPairAnswerResponse> {
        return repository.createAnswer(request, lang)
    }

    override fun deleteAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("matching_pair_answer_id_required", lang))
        return repository.deleteAnswer(id, lang)
    }
}