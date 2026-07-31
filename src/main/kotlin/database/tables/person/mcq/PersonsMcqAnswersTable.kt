package com.fathersprophets.backend.database.tables.person.mcq

import com.fathersprophets.backend.database.tables.users.UsersTable
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object PersonsMcqAnswersTable : Table("persons_mcq_answers") {
    val id = integer("id").autoIncrement()
    val answer = varchar("answer", 255)
    val questionId = reference("question_id", PersonsMcqTable.id, onDelete = ReferenceOption.CASCADE).index("idx_persons_mcq_answers_question_id")
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_persons_mcq_answers_user_id")
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    ).index("idx_persons_mcq_answers_status")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(questionId, userId)
    }
}