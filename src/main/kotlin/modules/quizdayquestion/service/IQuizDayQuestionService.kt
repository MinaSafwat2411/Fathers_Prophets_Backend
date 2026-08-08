package com.fathersprophets.backend.modules.quizdayquestion.service

import com.fathersprophets.backend.base.ApiResponse
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionCreateDto
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionDto
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionUpdateDto

interface IQuizDayQuestionService {
    fun getAll(lang: String): ApiResponse<List<QuizDayQuestionDto>>
    fun getById(id: Int, lang: String): ApiResponse<QuizDayQuestionDto>
    fun getByQuizDayId(quizDayId: Int, lang: String): ApiResponse<List<QuizDayQuestionDto>>
    fun create(dto: QuizDayQuestionCreateDto, lang: String): ApiResponse<QuizDayQuestionDto>
    fun createAll(dtos: List<QuizDayQuestionCreateDto>, lang: String): ApiResponse<List<QuizDayQuestionDto>>
    fun update(id: Int, dto: QuizDayQuestionUpdateDto, lang: String): ApiResponse<QuizDayQuestionDto>
    fun delete(id: Int, lang: String): ApiResponse<Nothing>
}