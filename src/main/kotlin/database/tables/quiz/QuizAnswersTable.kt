package com.fathersprophets.backend.database.tables.quiz

import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object QuizAnswersTable : Table("quiz_answers") {
    val id = integer("id").autoIncrement()
    val quizId = reference("quiz_id", QuizTable.id)
    val questionId = reference("question_id", QuizDayQuestionsTable.id)
    val dayId = reference("day_id", QuizDayTable.id)
    val userId = reference("user_id", UsersTable.id)
    val answer = varchar("answer", 255)
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(questionId, userId, dayId, quizId)
    }
}