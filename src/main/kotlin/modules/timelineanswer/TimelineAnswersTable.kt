package com.fathersprophets.backend.modules.timelineanswer

import com.fathersprophets.backend.database.enums.AnswerStatus
import com.fathersprophets.backend.modules.timeline.TimelineTable
import com.fathersprophets.backend.modules.user.UsersTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object TimelineAnswersTable : Table("timeline_answers") {
    val id = integer("id").autoIncrement()
    val timelineId = reference("timeline_id", TimelineTable.id)
    val userId = reference("user_id", UsersTable.id)
    val order = array<Int>("correct_order")
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(timelineId, userId)

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