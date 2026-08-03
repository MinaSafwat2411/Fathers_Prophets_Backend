package com.fathersprophets.backend.database.tables.guessperson

import com.fathersprophets.backend.database.enums.DifficultyType
import com.fathersprophets.backend.database.tables.person.PersonsTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object GuessPersonTable : Table("guess_person_questions") {
    val id = integer("id").autoIncrement()
    val question = varchar("question", 255)
    val correctPersonId = reference("correct_person_id", PersonsTable.id)
    val difficulty = customEnumeration(
        "difficulty",
        "difficulty_type",
        { value -> DifficultyType.valueOf(value as String) },
        { PGobject().apply { type = "difficulty_type"; value = it.name } }
    )
    val first = reference("first",PersonsTable.id, onDelete = ReferenceOption.CASCADE)
    val second = reference("second",PersonsTable.id, onDelete = ReferenceOption.CASCADE)
    val third = reference("third",PersonsTable.id, onDelete = ReferenceOption.CASCADE)
    val fourth = reference("fourth",PersonsTable.id, onDelete = ReferenceOption.CASCADE)

    val correctAnswer = reference("correct_answer",PersonsTable.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
    
    init {
        TransactionManager.current().exec(
            """
                DO $$ BEGIN 
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'difficulty_type') THEN 
                        CREATE TYPE difficulty_type AS ENUM (
                            'Easy', 'Medium', 'Hard'
                        ); 
                        
                    END IF; 
                END $$;
            """.trimIndent()
        )
    }
}
