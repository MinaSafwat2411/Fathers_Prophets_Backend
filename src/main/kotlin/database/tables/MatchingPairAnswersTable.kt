package com.fathersprophets.backend.database.tables

import com.fathersprophets.backend.database.enums.AnswerStatus
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object MatchingPairAnswersTable : Table("matching_pairs_answers") {
    val id = integer("id").autoIncrement()
    val pairId = integer("pair_id").references(PairItemsTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_matching_pairs_answers_pair_id")
    val userId = integer("user_id").references(UsersTable.id, onDelete = ReferenceOption.CASCADE)
        .index("idx_matching_pairs_answers_user_id")

    val right = varchar("right", 255)
    val left = varchar("left", 255)

    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name.lowercase() } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(pairId, userId)
    }
}