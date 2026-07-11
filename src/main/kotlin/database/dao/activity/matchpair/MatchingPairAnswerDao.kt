package com.fathersprophets.backend.database.dao.activity.matchpaor

import com.fathersprophets.backend.database.tables.MatchingPairAnswersTable
import com.fathersprophets.backend.models.dto.MatchingPairAnswerDto
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MatchingPairAnswerDao {

    private fun resultRowToDto(row: ResultRow) = MatchingPairAnswerDto(
        id = row[MatchingPairAnswersTable.id],
        pairId = row[MatchingPairAnswersTable.pairId],
        userId = row[MatchingPairAnswersTable.userId],
        userPair = Json.decodeFromString<Map<Int, String>>(row[MatchingPairAnswersTable.userPair]),
        status = row[MatchingPairAnswersTable.status]
    )

    fun findAll() = transaction {
        MatchingPairAnswersTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        MatchingPairAnswersTable.selectAll().where { MatchingPairAnswersTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun findByPairId(pairId: Int) = transaction {
        MatchingPairAnswersTable.selectAll().where { MatchingPairAnswersTable.pairId eq pairId }
            .map { resultRowToDto(it) }
    }

    fun findByUserId(userId: Int) = transaction {
        MatchingPairAnswersTable.selectAll().where { MatchingPairAnswersTable.userId eq userId }
            .map { resultRowToDto(it) }
    }

    fun create(dto: MatchingPairAnswerDto) = transaction {
        MatchingPairAnswersTable.insert {
            it[pairId] = dto.pairId
            it[userId] = dto.userId
            it[userPair] = Json.encodeToString(dto.userPair)
            it[status] = dto.status
        } get MatchingPairAnswersTable.id
    }

    fun update(dto: MatchingPairAnswerDto) = transaction {
        MatchingPairAnswersTable.update({ MatchingPairAnswersTable.id eq dto.id }) {
            it[pairId] = dto.pairId
            it[userId] = dto.userId
            it[userPair] = Json.encodeToString(dto.userPair)
            it[status] = dto.status
        } > 0
    }

    fun updateStatus(dto: MatchingPairAnswerDto) = transaction {
        MatchingPairAnswersTable.update({ MatchingPairAnswersTable.id eq dto.id }) {
            it[status] = dto.status
        } > 0
    }

    fun delete(id: Int) = transaction {
        MatchingPairAnswersTable.deleteWhere { MatchingPairAnswersTable.id eq id } > 0
    }
}