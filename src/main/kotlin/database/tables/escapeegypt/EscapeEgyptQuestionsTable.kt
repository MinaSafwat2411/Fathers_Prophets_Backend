package com.fathersprophets.backend.database.tables.escapeegypt

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EscapeEgyptQuestionsTable : Table("escape_from_eqypt_questions") {
    val id = integer("id").autoIncrement()
    val escapeEgyptId = reference("escape_egypt_id", EscapeEgyptTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_escape_egypt_questions_escape_egypt_id")
    val question = varchar("question", 255)
    val correctAnswer = varchar("correct_answer", 255)

    override val primaryKey = PrimaryKey(id)
}