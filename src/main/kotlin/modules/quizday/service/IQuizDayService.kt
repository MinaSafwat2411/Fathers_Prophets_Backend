package com.fathersprophets.backend.modules.quizday.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.database.enums.DayOfWeek
import com.fathersprophets.backend.database.tables.quizday.QuizDayCreateDto
import com.fathersprophets.backend.database.tables.quizday.QuizDayDto
import com.fathersprophets.backend.database.tables.quizday.QuizDayUpdateDto

interface IQuizDayService {
    fun getAll(lang: String): ApiResponse<List<QuizDayDto>>
    fun getById(id: Int, lang: String): ApiResponse<QuizDayDto>
    fun getByQuizId(quizId: Int, lang: String): ApiResponse<List<QuizDayDto>>
    fun getByQuizAndDay(quizId: Int, dayName: DayOfWeek, lang: String): ApiResponse<QuizDayDto>
    fun create(dto: QuizDayCreateDto, lang: String): ApiResponse<QuizDayDto>
    fun update(id: Int, dto: QuizDayUpdateDto, lang: String): ApiResponse<QuizDayDto>
    fun delete(id: Int, lang: String): ApiResponse<Nothing>
}