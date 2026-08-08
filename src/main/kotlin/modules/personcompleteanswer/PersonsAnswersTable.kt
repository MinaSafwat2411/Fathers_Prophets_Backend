package com.fathersprophets.backend.modules.personcompleteanswer

import com.fathersprophets.backend.database.enums.AnswerStatus
import com.fathersprophets.backend.modules.personcomplete.PersonsQuestionsTable
import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object PersonsAnswersTable : Table("persons_answers") {
    val id = integer("id").autoIncrement()
    val answer = text("answer")
    val questionId = reference("question_id", PersonsQuestionsTable.id, onDelete = ReferenceOption.CASCADE).index("idx_persons_answers_question_id")
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_persons_answers_user_id")
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    ).index("idx_persons_answers_status").default(AnswerStatus.TEACHER_STILL_NOT_CORRECTED)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(questionId, userId)

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
