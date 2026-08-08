package com.fathersprophets.backend.modules.quiz.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.database.tables.quiz.QuizCreateDto
import com.fathersprophets.backend.database.tables.quiz.QuizDto
import com.fathersprophets.backend.database.tables.quiz.QuizUpdateDto

interface IQuizService {
    fun getAll(lang: String): ApiResponse<List<QuizDto>>
    fun getById(id: Int, lang: String): ApiResponse<QuizDto>
    fun getByNumber(number: Int, lang: String): ApiResponse<QuizDto>
    fun getByFamilyId(familyId: Int, lang: String): ApiResponse<List<QuizDto>>
    fun create(dto: QuizCreateDto, lang: String): ApiResponse<QuizDto>
    fun update(id: Int, dto: QuizUpdateDto, lang: String): ApiResponse<QuizDto>
    fun delete(id: Int, lang: String): ApiResponse<Nothing>
}