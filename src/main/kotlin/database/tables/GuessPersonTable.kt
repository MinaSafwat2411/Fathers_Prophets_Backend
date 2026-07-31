package com.fathersprophets.backend.database.tables

import com.fathersprophets.backend.database.enums.DifficultyType
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object GuessPersonTable : Table("guess_person_questions") {
    val id = integer("id").autoIncrement()
    val question = varchar("question", 255)
    val correctPersonId = reference("correct_person_id", PersonsTable.id)
    val difficulty = customEnumeration(
        "difficulty",
        "difficulty_type",
        { value -> DifficultyType.valueOf(value as String) },
        { PGobject().apply { type = "difficulty_type"; value = it.name.lowercase() } }
    )
    val first = integer("first").references(PersonsTable.id)
    val second = integer("second").references(PersonsTable.id)
    val third = integer("third").references(PersonsTable.id)
    val fourth = integer("fourth").references(PersonsTable.id)

    val correctAnswer = integer("correct_answer").references(PersonsTable.id)

    override val primaryKey = PrimaryKey(id)
}