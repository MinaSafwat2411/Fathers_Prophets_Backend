package com.fathersprophets.backend.database.tables.quizday

import com.fathersprophets.backend.database.enums.DayOfWeek
import com.fathersprophets.backend.database.enums.QuizDayType
import com.fathersprophets.backend.database.tables.quiz.QuizTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object QuizDayTable : Table("quiz_day") {
    val id = integer("id").autoIncrement()
    val quizId = reference("quiz_id", QuizTable.id, onDelete = ReferenceOption.CASCADE).index("idx_quiz_day_quiz_id")
    val dayName = customEnumeration(
        "day_name",
        "day_of_week",
        { value -> DayOfWeek.valueOf(value as String) },
        { PGobject().apply { type = "day_of_week"; value = it.name } }
    )
    val book = varchar("book", 255)
    val chapter = integer("chapter")
    val verseFrom = integer("verse_from")
    val verseTo = integer("verse_to")
    val typeDay = customEnumeration(
        "type_day",
        "quiz_day_type",
        { value -> QuizDayType.valueOf(value as String) },
        { PGobject().apply { type = "quiz_day_type"; value = it.name} }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(quizId, dayName)
        
        TransactionManager.current().exec(
            """
                DO $$ BEGIN 
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'day_of_week') THEN 
                        CREATE TYPE day_of_week AS ENUM (
                            'SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'
                        ); 
                    END IF; 
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'quiz_day_type') THEN 
                        CREATE TYPE quiz_day_type AS ENUM (
                            'QUIZ', 'REVISION'
                        ); 
                    END IF; 
                END $$;
            """.trimIndent()
        )
    }
}
