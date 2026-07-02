package com.fathersprophets.backend.utils

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.quiz.QuizResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object QuizBroadcaster {
    private val _quizFlow = MutableSharedFlow<ApiResponse<List<QuizResponse>>>()
    val quizFlow = _quizFlow.asSharedFlow()

    suspend fun broadcastQuizzes(quizzes: ApiResponse<List<QuizResponse>>) {
        _quizFlow.emit(quizzes)
    }
}