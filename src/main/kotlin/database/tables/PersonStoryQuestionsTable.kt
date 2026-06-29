package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object PersonStoryQuestionsTable : Table("persons_story_questions") {
    val id = integer("id").autoIncrement()
    val storyId = reference("story_id", PersonStoryTable.id, onDelete = ReferenceOption.CASCADE).index("idx_persons_story_questions_story_id")
    val question = varchar("question", 255)

    override val primaryKey = PrimaryKey(id)
}