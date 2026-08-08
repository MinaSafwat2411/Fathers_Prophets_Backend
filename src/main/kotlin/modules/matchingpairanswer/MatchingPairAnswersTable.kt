package com.fathersprophets.backend.modules.matchingpairanswer

import com.fathersprophets.backend.database.enums.AnswerStatus
import com.fathersprophets.backend.modules.matchpairitems.MatchingPairItemsTable
import com.fathersprophets.backend.database.tables.user.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object MatchingPairAnswersTable : Table("matching_pairs_answers") {
    val id = integer("id").autoIncrement()
    val pairId = reference("pair_id", MatchingPairItemsTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_matching_pairs_answers_pair_id")
    val userId = reference("user_id",UsersTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_matching_pairs_answers_user_id")

    val right = varchar("right", 255)
    val left = varchar("left", 255)

    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(pairId, userId)
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
