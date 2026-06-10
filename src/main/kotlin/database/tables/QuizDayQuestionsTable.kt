package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

enum class McqCorrectAnswer { `1`, `2`, `3`, `4` }

object QuizDayQuestionsTable : Table("quiz_day_questions") {
    val id = integer("id").autoIncrement()
    val quizDayId = reference("quiz_day_id", QuizDayTable.id)
    val question = text("question")
    val choice1 = varchar("choice_1", 255)
    val choice2 = varchar("choice_2", 255)
    val choice3 = varchar("choice_3", 255).nullable()
    val choice4 = varchar("choice_4", 255).nullable()
    val correctAnswer = customEnumeration(
        "correct_answer",
        "mcq_correct_answer",
        { value -> McqCorrectAnswer.valueOf(value as String) },
        { PGobject().apply { type = "mcq_correct_answer"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)
}