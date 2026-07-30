package com.fathersprophets.backend.database.tables.person.complete

import com.fathersprophets.backend.database.tables.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

enum class AnswerStatus {
    TEACHER_STILL_NOT_CORRECTED,
    IS_TRUE,
    IS_FALSE
}

object PersonsAnswersTable : Table("persons_answers") {
    val id = integer("id").autoIncrement()
    val answer = text("answer")
    val questionId = reference("question_id", PersonsQuestionsTable.id, onDelete = ReferenceOption.CASCADE).index("idx_persons_answers_question_id")
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_persons_answers_user_id")
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    ).index("idx_persons_answers_status")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(questionId, userId)
    }
}