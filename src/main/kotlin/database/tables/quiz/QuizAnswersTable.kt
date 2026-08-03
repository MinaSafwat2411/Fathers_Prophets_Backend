package com.fathersprophets.backend.database.tables.quiz

import com.fathersprophets.backend.database.enums.AnswerStatus
import com.fathersprophets.backend.database.enums.McqCorrectAnswer
import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object QuizAnswersTable : Table("quiz_answers") {
    val id = integer("id").autoIncrement()
    val questionId = reference("question_id", QuizDayQuestionsTable.id)
    val userId = reference("user_id", UsersTable.id)
    val answer = varchar("answer", 255)
    val answerOrder = customEnumeration(
        "answer_order",
        "answer_order_type",
        { value -> McqCorrectAnswer.valueOf(value as String) },
        { PGobject().apply { type = "answer_order_type"; value = it.name } }
    )

    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    ).default(AnswerStatus.TEACHER_STILL_NOT_CORRECTED)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(questionId, userId)
        
        TransactionManager.current().exec(
            """
                DO $$ BEGIN 
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'answer_order_type') THEN 
                        CREATE TYPE answer_order_type AS ENUM (
                            'First', 'Second', 'Third', 'Fourth'
                        ); 
                    END IF; 
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
