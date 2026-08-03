package com.fathersprophets.backend.database.tables.personmcq

import com.fathersprophets.backend.database.enums.McqCorrectAnswer
import com.fathersprophets.backend.database.tables.person.PersonsTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject


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

    init {
        TransactionManager.current().exec(
            """
                DO $$ BEGIN 
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'mcq_correct_answer') THEN 
                        CREATE TYPE mcq_correct_answer AS ENUM (
                            'First', 'Second', 'Third', 'Fourth'
                        ); 
                    END IF; 
                END $$;
            """.trimIndent()
        )
    }
}