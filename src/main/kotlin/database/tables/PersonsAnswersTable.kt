package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

enum class AnswerStatus {
    TEACHER_STILL_NOT_CORRECTED,
    IS_TRUE,
    IS_FALSE
}

object PersonsAnswersTable : Table("persons_answers") {
    val id = integer("id").autoIncrement()
    val answer = text("answer")
    val questionId = integer("question_id").references(PersonsQuestionsTable.id, onDelete = ReferenceOption.CASCADE)
    val userId = integer("user_id").references(UsersTable.id, onDelete = ReferenceOption.CASCADE)
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { it.name }
    )

    override val primaryKey = PrimaryKey(id)
}