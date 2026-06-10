package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object GuessPersonAnswersTable : Table("guess_person_answers") {
    val id = integer("id").autoIncrement()
    val questionId = reference("question_id", GuessPersonTable.id)
    val userId = reference("user_id", UsersTable.id)
    val personId = reference("person_id", PersonsTable.id)
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(questionId, userId)
    }
}