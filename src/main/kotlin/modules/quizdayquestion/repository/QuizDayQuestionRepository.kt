package com.fathersprophets.backend.modules.quizdayquestion.repository

import com.fathersprophets.backend.base.BaseRepository
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionCreateDto
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionDto
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionUpdateDto
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionsDao

class QuizDayQuestionRepository(
    quizDayQuestionsDao: QuizDayQuestionsDao
) : BaseRepository<QuizDayQuestionDto, QuizDayQuestionCreateDto, QuizDayQuestionUpdateDto, QuizDayQuestionsDao>(
    quizDayQuestionsDao
), IQuizDayQuestionRepository {

    override fun getByQuizDayId(quizDayId: Int): List<QuizDayQuestionDto> = dao.getByQuizDayId(quizDayId)

    override fun createAll(dtos: List<QuizDayQuestionCreateDto>): List<QuizDayQuestionDto> =
        dtos.mapNotNull { dao.create(it) }
}