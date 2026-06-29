package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.Table
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
        uniqueIndex(userId, questionId)
    }
}