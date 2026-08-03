package com.fathersprophets.backend.database.tables.escapeegyptanswer

import com.fathersprophets.backend.database.enums.AnswerStatus
import com.fathersprophets.backend.database.tables.escapeegyptquestion.EscapeEgyptQuestionsTable
import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object EscapeEgyptAnswersTable : Table("escape_egypt_answers") {
    val id = integer("id").autoIncrement()
    val escapeQuestionId = reference("escape_question_id", EscapeEgyptQuestionsTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_escape_egypt_answers_escape_question_id")
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_escape_egypt_answers_user_id")
    val answer = varchar("answer", 255)
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(escapeQuestionId, userId)

        TransactionManager.current().exec(
            """
                DO $$ BEGIN 
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'answer_status') THEN 
                        CREATE TYPE answer_status AS ENUM (
                            'TEACHER_STILL_NOT_CORRECTED', 'IS_TRUE', 'IS_FALSE'
                        ); 
                    END IF; 
                END $$;
            """.trimIndent()
        )
    }
}
