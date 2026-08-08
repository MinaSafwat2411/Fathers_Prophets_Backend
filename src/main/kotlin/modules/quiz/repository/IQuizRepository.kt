package com.fathersprophets.backend.modules.quiz.repository

import com.fathersprophets.backend.database.tables.quiz.QuizDto

interface IQuizRepository {
    fun getByNumber(number: Int): QuizDto?
    fun getByFamilyId(familyId: Int): List<QuizDto>
}