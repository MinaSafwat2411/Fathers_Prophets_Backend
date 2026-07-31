package com.fathersprophets.backend.database.tables.activity.timeline

import com.fathersprophets.backend.database.tables.users.UsersTable
import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object TimelineAnswersTable : Table("timeline_answers") {
    val id = integer("id").autoIncrement()
    val timelineId = reference("timeline_id", TimelineTable.id)
    val userId = reference("user_id", UsersTable.id)
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(timelineId, userId)
    }
}