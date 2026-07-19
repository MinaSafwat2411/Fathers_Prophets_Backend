package com.fathersprophets.backend.services.person.guesspersonanswer

import com.fathersprophets.backend.database.repository.person.guessperson.guesspersonanswer.IGuessPersonAnswerRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.guesspersonanswer.CreateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.GuessPersonAnswerResponse
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerRequest
import com.fathersprophets.backend.models.guesspersonanswer.UpdateGuessPersonAnswerStatusRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class GuessPersonAnswerService(
    private val repository: IGuessPersonAnswerRepository
) : IGuessPersonAnswerService {

    override fun getAllAnswers(lang: String): ApiResponse<List<GuessPersonAnswerResponse>> {
        return repository.getAllAnswers(lang)
    }

    override fun getAnswersByUserIdAndQuestionId(
        userId: Int?,
        questionId: Int?,
        lang: String
    ): ApiResponse<List<GuessPersonAnswerResponse>> {
        if (userId == null) throw IllegalArgumentException(Localization.get("user_id_required", lang))
        if (questionId == null) throw IllegalArgumentException(Localization.get("question_id_required", lang))

        return repository.getAnswersByUserIdAndQuestionId(userId, questionId, lang)
    }


    override fun createAnswer(request: CreateGuessPersonAnswerRequest, lang: String): ApiResponse<GuessPersonAnswerResponse> {
        return repository.createAnswer(request, lang)
    }

    override fun deleteAnswer(id: Int?, lang: String): ApiResponse<Nothing> {
        if (id == null) throw IllegalArgumentException(Localization.get("guess_person_answer_id_required", lang))
        return repository.deleteAnswer(id, lang)
    }
}