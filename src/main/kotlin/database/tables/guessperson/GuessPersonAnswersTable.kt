package com.fathersprophets.backend.database.tables.guessperson

import com.fathersprophets.backend.database.enums.AnswerStatus
import com.fathersprophets.backend.database.tables.PersonsTable
import com.fathersprophets.backend.database.tables.UsersTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object GuessPersonAnswersTable : Table("guess_person_answers") {
    val id = integer("id").autoIncrement()
    val questionId = reference("question_id", GuessPersonTable.id)
    val userId = reference("user_id", UsersTable.id)
    val answer = reference("answer", PersonsTable.id)
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name.lowercase() } }
    ).default(AnswerStatus.TEACHER_STILL_NOT_CORRECTED)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(questionId, userId)
        TransactionManager.current().exec(
            """
                DO $$ BEGIN 
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'answer_status') THEN 
                        CREATE TYPE answer_status AS ENUM (
                            'CORRECT', 'WRONG', 'TEACHER_STILL_NOT_CORRECTED'
                        );
                    END IF;
                END $$;
            """.trimIndent()
        )
    }
}
