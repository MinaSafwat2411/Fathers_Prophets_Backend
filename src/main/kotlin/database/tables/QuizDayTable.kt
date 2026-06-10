package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.postgresql.util.PGobject

enum class DayOfWeek { SAT, SUN, MON, TUE, WED, THU, FRI }
enum class QuizDayType { TRUE_FALSE, MCQ }

object QuizDayTable : Table("quiz_day") {
    val id = integer("id").autoIncrement()
    val quizId = reference("quiz_id", QuizTable.id, onDelete = ReferenceOption.CASCADE).index("idx_quiz_day_quiz_id")
    val dayName = customEnumeration(
        "day_name",
        "day_of_week",
        { value -> DayOfWeek.valueOf(value as String) },
        { PGobject().apply { type = "day_of_week"; value = it.name } }
    )
    val startAt = timestamp("start_at").index("idx_quiz_day_start_at")
    val endAt = timestamp("end_at").index("idx_quiz_day_end_at")
    val book = varchar("book", 255)
    val chapter = integer("chapter")
    val verseFrom = integer("verse_from")
    val verseTo = integer("verse_to")
    val typeDay = customEnumeration(
        "type_day",
        "quiz_day_type",
        { value -> QuizDayType.valueOf(value as String) },
        { PGobject().apply { type = "quiz_day_type"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(quizId, dayName)
    }
}