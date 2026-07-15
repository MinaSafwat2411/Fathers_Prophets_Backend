package com.fathersprophets.backend.database.tables.person.guessperson

import com.fathersprophets.backend.database.tables.person.mcq.McqCorrectAnswer
import com.fathersprophets.backend.database.tables.person.PersonsTable
import com.fathersprophets.backend.database.tables.json
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object GuessPersonTable : Table("guess_person_questions") {
    val id = integer("id").autoIncrement()
    val question = varchar("question", 255)
    val correctPersonId = reference("correct_person_id", PersonsTable.id)
    val difficulty = integer("difficulty").nullable()
    val first = json("first")
    val second = json("second")
    val third = json("third")
    val fourth = json("fourth")
    val correctAnswer = customEnumeration(
        "correct_answer",
        "mcq_correct_answer",
        { value -> McqCorrectAnswer.valueOf(value as String) },
        { PGobject().apply { type = "mcq_correct_answer"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)
}