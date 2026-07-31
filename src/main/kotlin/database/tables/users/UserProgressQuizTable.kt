package com.fathersprophets.backend.database.tables.users

import com.fathersprophets.backend.database.tables.quiz.QuizDayTable
import com.fathersprophets.backend.database.tables.quiz.QuizTable
import org.jetbrains.exposed.sql.Table

object UserProgressQuizTable : Table("user_progress_quiz") {
    val id = integer("id").autoIncrement()
    val userId = reference("user_id", UsersTable.id)
    val quizId = reference("quiz_id", QuizTable.id)
    val dayId = reference("day_id", QuizDayTable.id)
    val score = integer("score").default(0)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(userId, quizId, dayId)
    }
}