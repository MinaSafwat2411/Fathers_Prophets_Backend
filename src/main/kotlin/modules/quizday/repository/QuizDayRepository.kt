package com.fathersprophets.backend.modules.quizday.repository

import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.database.enums.DayOfWeek
import com.fathersprophets.backend.database.tables.quizday.QuizDayCreateDto
import com.fathersprophets.backend.database.tables.quizday.QuizDayDao
import com.fathersprophets.backend.database.tables.quizday.QuizDayDto
import com.fathersprophets.backend.database.tables.quizday.QuizDayUpdateDto

class QuizDayRepository(
    quizDayDao: QuizDayDao
) : BaseRepository<QuizDayDto, QuizDayCreateDto, QuizDayUpdateDto, QuizDayDao>(quizDayDao), IQuizDayRepository {

    override fun getByQuizId(quizId: Int): List<QuizDayDto> = dao.getByQuizId(quizId)

    override fun getByQuizAndDay(quizId: Int, dayName: DayOfWeek): QuizDayDto? = dao.getByQuizAndDay(quizId, dayName)
}