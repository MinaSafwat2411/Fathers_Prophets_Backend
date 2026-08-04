package com.fathersprophets.backend.modules.quiz

import com.fathersprophets.backend.modules.family.FamilyTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object QuizTable : Table("quiz") {
    val id = integer("id").autoIncrement()
    val number = integer("number").uniqueIndex()
    val startAt = timestamp("start_at")
    val endAt = timestamp("end_at")
    val title = varchar("title", 255)
    val familyId = reference("family_id", FamilyTable.id, onDelete = ReferenceOption.CASCADE).index("idx_quiz_family_id")

    override val primaryKey = PrimaryKey(id)
}