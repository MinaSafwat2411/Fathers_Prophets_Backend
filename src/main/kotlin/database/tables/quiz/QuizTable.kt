package com.fathersprophets.backend.database.tables.quiz

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object QuizTable : Table("quiz") {
    val id = integer("id").autoIncrement()
    val number = integer("number").uniqueIndex()
    val startAt = timestamp("start_at")
    val endAt = timestamp("end_at")

    override val primaryKey = PrimaryKey(id)
}