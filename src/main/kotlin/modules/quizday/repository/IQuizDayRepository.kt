package com.fathersprophets.backend.modules.quizday.repository

import com.fathersprophets.backend.database.enums.DayOfWeek
import com.fathersprophets.backend.database.tables.quizday.QuizDayDto

interface IQuizDayRepository {
    fun getByQuizId(quizId: Int): List<QuizDayDto>
    fun getByQuizAndDay(quizId: Int, dayName: DayOfWeek): QuizDayDto?
}