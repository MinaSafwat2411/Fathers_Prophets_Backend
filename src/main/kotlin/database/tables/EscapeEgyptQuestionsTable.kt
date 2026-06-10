package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table

object EscapeEgyptQuestionsTable : Table("escape_from_eqypt_questions") {
    val id = integer("id").autoIncrement()
    val escapeEgyptId = reference("escape_egypt_id", EscapeEgyptTable.id)
    val question = varchar("question", 255)
    val correctAnswer = varchar("correct_answer", 255)

    override val primaryKey = PrimaryKey(id)
}