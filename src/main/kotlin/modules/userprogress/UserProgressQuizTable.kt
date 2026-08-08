package com.fathersprophets.backend.modules.userprogress

import com.fathersprophets.backend.database.tables.quizday.QuizDayTable
import com.fathersprophets.backend.database.tables.quiz.QuizTable
import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UserProgressQuizTable : Table("user_progress_quiz") {
    val id = integer("id").autoIncrement()
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_user_progress_quiz_user_id")
    val quizId = reference("quiz_id", QuizTable.id, onDelete = ReferenceOption.CASCADE).index("idx_user_progress_quiz_quiz_id")
    val dayId = reference("day_id", QuizDayTable.id, onDelete = ReferenceOption.CASCADE).index("idx_user_progress_quiz_day_id")
    val score = integer("score").default(0)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(userId, quizId, dayId)
    }
}