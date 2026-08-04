package com.fathersprophets.backend.modules.escapeegyptquestion

import com.fathersprophets.backend.modules.escapeegypt.EscapeEgyptTable
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