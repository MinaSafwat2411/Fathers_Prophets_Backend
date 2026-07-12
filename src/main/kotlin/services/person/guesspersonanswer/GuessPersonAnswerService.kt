package com.fathersprophets.backend.services.person.guesspersonanswer

import com.fathersprophets.backend.database.repository.person.guessperson.guesspersonanswer.IGuessPersonAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.guesspersonanswer.CreateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.GuessPersonAnswerResponse
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerStatusRequest
import com.fathersprophets.backend.services.guesspersonanswer.IGuessPersonAnswerService
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class GuessPersonAnswerService(
    private val repository: IGuessPersonAnswerRepository
) : IGuessPersonAnswerService {

    override fun getAllAnswers(lang: String): ApiResponse<List<GuessPersonAnswerResponse>> {
        return repository.getAllAnswers(lang)
    }

    override fun getAnswerById(id: Int?, lang: String): ApiResponse<GuessPersonAnswerResponse> {
        if (id == null) throw IllegalArgumentException(Localization.get("guess_person_answer_id_required", lang))
        return repository.getAnswerById(id, lang)
    }

    override fun getAnswersByQuestionId(questionId: Int?, lang: String): ApiResponse<List<GuessPersonAnswerResponse>> {
        if (questionId == null) throw IllegalArgumentException(Localization.get("question_id_required", lang))
        return repository.getAnswersByQuestionId(questionId, lang)
    }

    override fun getAnswersByUserId(userId: Int?, lang: String): ApiResponse<List<GuessPersonAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        return repository.getAnswersByUserId(userId, lang)
    }

    override fun createAnswer(request: CreateGuessPersonAnswerRequest, lang: String): ApiResponse<Int> {
        return repository.createAnswer(request, lang)
    }

    override fun updateAnswer(id: Int?, request: UpdateGuessPersonAnswerRequest, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("guess_person_answer_id_required", lang))
        return repository.updateAnswer(id, request, lang)
    }

    override fun updateAnswerStatus(id: Int?, request: UpdateGuessPersonAnswerStatusRequest, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("guess_person_answer_id_required", lang))
        validateRequired(request.status to "status", lang = lang)
        return repository.updateAnswerStatus(id, request, lang)
    }

    override fun deleteAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("guess_person_answer_id_required", lang))
        return repository.deleteAnswer(id, lang)
    }
}