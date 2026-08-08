package com.fathersprophets.backend.modules.quiz.repository

import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.database.tables.quiz.QuizCreateDto
import com.fathersprophets.backend.database.tables.quiz.QuizDao
import com.fathersprophets.backend.database.tables.quiz.QuizDto
import com.fathersprophets.backend.database.tables.quiz.QuizUpdateDto

class QuizRepository(
    quizDao: QuizDao
) : BaseRepository<QuizDto, QuizCreateDto, QuizUpdateDto, QuizDao>(quizDao), IQuizRepository {

    override fun getByNumber(number: Int): QuizDto? = dao.getByNumber(number)

    override fun getByFamilyId(familyId: Int): List<QuizDto> = dao.getByFamilyId(familyId)
}