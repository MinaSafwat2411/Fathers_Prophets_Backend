package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

private class JsonColumnType : ColumnType<String>() {
    override fun sqlType() = "JSON"
    override fun valueFromDB(value: Any): String = when (value) {
        is PGobject -> value.value ?: "{}"
        else -> value.toString()
    }
    override fun notNullValueToDB(value: String) = PGobject().apply {
        type = "json"
        this.value = value
    }
}

private fun Table.json(name: String): Column<String> = registerColumn(name, JsonColumnType())

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