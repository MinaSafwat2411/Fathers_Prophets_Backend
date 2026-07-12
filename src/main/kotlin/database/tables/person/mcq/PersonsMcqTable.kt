package com.fathersprophets.backend.database.tables.person.mcq

import com.fathersprophets.backend.database.tables.person.PersonsTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

enum class McqCorrectAnswer {
    `1`,
    `2`,
    `3`,
    `4`
}

object PersonsMcqTable : Table("persons_mcq") {
    val id = integer("id").autoIncrement()
    val personId = reference("person_id", PersonsTable.id, onDelete = ReferenceOption.CASCADE)
    val question = varchar("question", 255)
    val first = varchar("first", 255)
    val second = varchar("second", 255)
    val third = varchar("third", 255)
    val fourth = varchar("fourth", 255)
    val correctAnswer = customEnumeration(
        "correct_answer",
        "mcq_correct_answer",
        { value -> McqCorrectAnswer.valueOf(value as String) },
        { PGobject().apply { type = "mcq_correct_answer"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)
}