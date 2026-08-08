package com.fathersprophets.backend.modules.quizdayquestion.repository

import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionCreateDto
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionDto

interface IQuizDayQuestionRepository {
    fun getByQuizDayId(quizDayId: Int): List<QuizDayQuestionDto>
    fun createAll(dtos: List<QuizDayQuestionCreateDto>): List<QuizDayQuestionDto>
}