package com.fathersprophets.backend.database.tables.activity.matchingair

import com.fathersprophets.backend.database.tables.person.complete.AnswerStatus
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.database.tables.json
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

object MatchingPairAnswersTable : Table("matching_pairs_answers") {
    val id = integer("id").autoIncrement()
    val pairId = reference("pair_id", MatchingPairTable.id)
    val userId = reference("user_id", UsersTable.id)
    val userPair = json("user_pair")
    val status = customEnumeration(
        "status",
        "answer_status",
        { value -> AnswerStatus.valueOf(value as String) },
        { PGobject().apply { type = "answer_status"; value = it.name } }
    )

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(pairId, userId)
    }
}