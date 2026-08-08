package com.fathersprophets.backend.modules.personstoryanswer

import com.fathersprophets.backend.database.enums.AnswerStatus
import com.fathersprophets.backend.modules.personstory.PersonStoryTable
import com.fathersprophets.backend.modules.personstoryquestion.PersonStoryQuestionsTable
import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object PersonStoryAnswersTable : Table("stories_answers") {
    val id = integer("id").autoIncrement()
    val storyId = reference("story_id", PersonStoryTable.id)
    val userId = reference("user_id", UsersTable.id)
    val answered = varchar("answered", 255)
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    )
    val questionId = reference("question_id", PersonStoryQuestionsTable.id)


    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(storyId, userId, questionId)

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