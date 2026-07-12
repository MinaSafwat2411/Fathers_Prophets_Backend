package com.fathersprophets.backend.database.repository.activity.matchingpair.matchingpairanswer

import com.fathersprophets.backend.database.dao.activity.matchpaor.MatchingPairAnswerDao
import com.fathersprophets.backend.database.dao.activity.matchpair.MatchingPairDao
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.matchingpairanswer.CreateMatchingPairAnswerRequest
import com.fathersprophets.backend.models.matchingpairanswer.MatchingPairAnswerResponse
import com.fathersprophets.backend.models.matchingpairanswer.UpdateMatchingPairAnswerRequest
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

    override fun getAnswerById(id: Int, lang: String): ApiResponse<MatchingPairAnswerResponse> {
        val answer = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = answer?.convertToResponse(),
            message = Localization.get("matching_pair_answer_retrieved_successfully", lang)
        )
    }

    override fun getAnswersByPairId(pairId: Int, lang: String): ApiResponse<List<MatchingPairAnswerResponse>> {
        val answers = answerDao.findByPairId(pairId)
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

    override fun createAnswer(request: CreateMatchingPairAnswerRequest, lang: String): ApiResponse<Int> {

        pairDao.findById(request.pairId)
            ?: throw IllegalArgumentException(Localization.get("matching_pair_not_found", lang))

        val status = computeStatus(request.userPair)

        val id = answerDao.create(request.convertToDto().copy(status = status))

        if (id == 0) throw IllegalArgumentException(Localization.get("matching_pair_answer_creation_failed", lang))

        return ApiResponse(
            success = true,
            data = id,
            message = Localization.get("matching_pair_answer_created_successfully", lang)
        )
    }

    override fun updateAnswer(id: Int, request: UpdateMatchingPairAnswerRequest, lang: String): ApiResponse<Nothing> {
        pairDao.findById(request.pairId)
            ?: throw IllegalArgumentException(Localization.get("matching_pair_not_found", lang))

        val status = computeStatus(request.userPair)

        val update = answerDao.update(request.convertToDto(id).copy(status = status))

        if (!update) throw IllegalArgumentException(Localization.get("matching_pair_answer_update_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("matching_pair_answer_updated_successfully", lang)
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

    private fun computeStatus(userPair: Map<Int, String>): AnswerStatus {
        if (userPair.isEmpty()) return AnswerStatus.IS_FALSE

        val correctPairs = pairDao.findAll()
        if (correctPairs.size != userPair.size) return AnswerStatus.IS_FALSE

        val correctBySide = correctPairs.associate { it.personId to it.otherSide }
        val allCorrect = userPair.all { (personId, otherSide) -> correctBySide[personId] == otherSide }
        return if (allCorrect) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE
    }
}