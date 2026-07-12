package com.fathersprophets.backend.database.tables.person.complete

import com.fathersprophets.backend.database.tables.person.PersonsTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

enum class QuestionType {
    mcq,
    complete
}

object PersonsQuestionsTable : Table("persons_questions") {
    val id = integer("id").autoIncrement()
    val question = varchar("question", 255)
    val personId = reference("person_id", PersonsTable.id, onDelete = ReferenceOption.CASCADE).index("idx_persons_questions_person_id")
    val type = customEnumeration(
        "type",
        "question_type",
        { value -> QuestionType.valueOf(value as String) },
        { PGobject().apply { type = "question_type"; value = it.name } }
    ).index("idx_persons_questions_type")

    override val primaryKey = PrimaryKey(id)
}