package com.fathersprophets.backend.database.tables

import com.fathersprophets.backend.database.enums.AnswerStatus
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object EscapeEgyptAnswersTable : Table("escape_egypt_answers") {
    val id = integer("id").autoIncrement()
    val escapeEgyptId = reference("escape_egypt_id", EscapeEgyptTable.id)
    val escapeQuestionId = reference("escape_question_id", EscapeEgyptQuestionsTable.id)
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
        uniqueIndex(escapeQuestionId, userId)
    }
}