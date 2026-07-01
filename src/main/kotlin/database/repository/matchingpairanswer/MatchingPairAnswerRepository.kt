package com.fathersprophets.backend.database.repository.matchingpairanswer

import com.fathersprophets.backend.database.dao.MatchingPairAnswerDao
import com.fathersprophets.backend.database.dao.MatchingPairDao
import com.fathersprophets.backend.database.tables.AnswerStatus
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.MatchingPairAnswerDto
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

    override fun createAnswer(request: CreateMatchingPairAnswerRequest, lang: String): ApiResponse<MatchingPairAnswerResponse> {
        val existing = answerDao.findByPairIdAndUserId(request.pairId, request.userId)
        if (existing != null) throw IllegalStateException(Localization.get("matching_pair_answer_already_exists", lang))

        pairDao.findById(request.pairId)
            ?: throw IllegalArgumentException(Localization.get("matching_pair_not_found", lang))

        val status = computeStatus(request.userPair)

        val id = answerDao.create(
            MatchingPairAnswerDto(
                id = 0,
                pairId = request.pairId,
                userId = request.userId,
                userPair = request.userPair,
                status = status
            )
        )
        val created = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("matching_pair_answer_created_successfully", lang)
        )
    }

    override fun updateAnswer(id: Int, request: UpdateMatchingPairAnswerRequest, lang: String): ApiResponse<MatchingPairAnswerResponse> {
        pairDao.findById(request.pairId)
            ?: throw IllegalArgumentException(Localization.get("matching_pair_not_found", lang))

        val status = computeStatus(request.userPair)

        answerDao.update(
            MatchingPairAnswerDto(
                id = id,
                pairId = request.pairId,
                userId = request.userId,
                userPair = request.userPair,
                status = status
            )
        )
        val updated = answerDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("matching_pair_answer_updated_successfully", lang)
        )
    }

    override fun deleteAnswer(id: Int, lang: String): ApiResponse<Nothing> {
        answerDao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("matching_pair_answer_deleted_successfully", lang)
        )
    }

    private fun computeStatus(userPair: Map<Int, String>): AnswerStatus {
        if (userPair.isEmpty()) return AnswerStatus.IS_FALSE

        val correctPairs = pairDao.findByPersonIds(userPair.keys.toList())
        if (correctPairs.size != userPair.size) return AnswerStatus.IS_FALSE

        val correctBySide = correctPairs.associate { it.personId to it.otherSide }
        val allCorrect = userPair.all { (personId, otherSide) -> correctBySide[personId] == otherSide }
        return if (allCorrect) AnswerStatus.IS_TRUE else AnswerStatus.IS_FALSE
    }

    private fun idToDto(id: Int) = MatchingPairAnswerDto(
        id = id,
        pairId = 0,
        userId = 0,
        userPair = emptyMap(),
        status = AnswerStatus.IS_FALSE
    )
}