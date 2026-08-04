package com.fathersprophets.backend.database.repository.activity.matchingpair.matchingpairanswer

import com.fathersprophets.backend.database.dao.MatchingPairDao
import com.fathersprophets.backend.database.dao.activity.matchpaor.MatchingPairAnswerDao
import com.fathersprophets.backend.modules.person.complete.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.MatchingPairDto
import com.fathersprophets.backend.models.matchingpairanswer.CreateMatchingPairAnswerRequest
import com.fathersprophets.backend.models.matchingpairanswer.MatchingPairAnswerResponse
import com.fathersprophets.backend.utils.Localization

class MatchingPairAnswerRepository(
    private val answerDao: MatchingPairAnswerDao,
    private val pairDao: MatchingPairDao
) : IMatchingPairAnswerRepository {

    override fun getAllAnswers(lang: String): ApiResponse<List<MatchingPairAnswerResponse>> {
        val answers = answerDao.findAll()
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("matching_pair_answers_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByUserId(userId: Int, lang: String): ApiResponse<List<MatchingPairAnswerResponse>> {
        val answers = answerDao.findByUserId(userId)
        return ApiResponse(
            success = true,
            data = answers.map { it.convertToResponse() },
            message = Localization.get("matching_pair_answers_retrieved_successfully", lang)
        )
    }

    override fun createAnswer(
        request: CreateMatchingPairAnswerRequest,
        lang: String
    ): ApiResponse<MatchingPairAnswerResponse> {

        val pair = pairDao.findById(request.pairId)
            ?: throw IllegalArgumentException(Localization.get("matching_pair_not_found", lang))

        val status = computeStatus(request.userPair, pair)

        val created = answerDao.create(request.convertToDto().copy(status = status))
            ?: throw IllegalArgumentException(Localization.get("matching_pair_answer_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = created.convertToResponse(),
            message = Localization.get("matching_pair_answer_created_successfully", lang)
        )
    }

    override fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing> {

        val delete = answerDao.delete(id)

        if (!delete) throw IllegalArgumentException(Localization.get("matching_pair_answer_delete_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("matching_pair_answer_deleted_successfully", lang)
        )
    }

    private fun computeStatus(userPair: Map<Int, String>, pair: MatchingPairDto): AnswerStatus {
        val allCorrect = pair.personId == userPair.keys.first() && pair.otherSide == userPair.values.first()
        return if (allCorrect) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE
    }
}