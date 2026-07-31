package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object PersonsQuestionsTable : Table("persons_questions") {
    val id = integer("id").autoIncrement()
    val question = varchar("question", 255)
    val personId = reference(
        "person_id",
        PersonsTable.id,
        onDelete = ReferenceOption.CASCADE
    ).index("idx_persons_questions_person_id")
    val correctAnswer = varchar("correct_answer", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}