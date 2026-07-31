package com.fathersprophets.backend.database.tables

import com.fathersprophets.backend.database.enums.AnswerStatus
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object GuessPersonAnswersTable : Table("guess_person_answers") {
    val id = integer("id").autoIncrement()
    val questionId = reference("question_id", GuessPersonTable.id)
    val userId = reference("user_id", UsersTable.id)
    val answer = reference("answer", PersonsTable.id)
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name.lowercase() } }
    ).default(AnswerStatus.TEACHER_STILL_NOT_CORRECTED)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(questionId, userId)
    }
}