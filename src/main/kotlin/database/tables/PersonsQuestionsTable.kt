package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

enum class QuestionType {
    mcq,
    complete
}

object PersonsQuestionsTable : Table("persons_questions") {
    val id = integer("id").autoIncrement()
    val question = varchar("question", 255)
    val personId = integer("person_id").references(PersonsTable.id, onDelete = ReferenceOption.CASCADE)
    val type = customEnumeration(
        "type",
        "question_type",
        { value -> QuestionType.valueOf(value as String) },
        { it.name }
    )

    override val primaryKey = PrimaryKey(id)
}