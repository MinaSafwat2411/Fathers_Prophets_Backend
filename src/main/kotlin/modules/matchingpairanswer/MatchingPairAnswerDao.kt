package com.fathersprophets.backend.modules.matchingpairanswer

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MatchingPairAnswerDao {

    private fun ResultRow.toDto() = MatchingPairAnswerDto(
        id = this[MatchingPairAnswersTable.id],
        pairId = this[MatchingPairAnswersTable.pairId],
        userId = this[MatchingPairAnswersTable.userId],
        right = this[MatchingPairAnswersTable.right],
        left = this[MatchingPairAnswersTable.left],
        status = this[MatchingPairAnswersTable.status]
    )

    fun getAll() = transaction {
        MatchingPairAnswersTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        MatchingPairAnswersTable.selectAll()
            .where { MatchingPairAnswersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByPairId(pairId: Int) = transaction {
        MatchingPairAnswersTable.selectAll()
            .where { MatchingPairAnswersTable.pairId eq pairId }
            .map { it.toDto() }
    }

    fun getByUserId(userId: Int) = transaction {
        MatchingPairAnswersTable.selectAll()
            .where { MatchingPairAnswersTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByPairAndUser(pairId: Int, userId: Int) = transaction {
        MatchingPairAnswersTable.selectAll()
            .where { (MatchingPairAnswersTable.pairId eq pairId) and (MatchingPairAnswersTable.userId eq userId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: MatchingPairAnswerCreateDto) = transaction {
        MatchingPairAnswersTable.insert {
            it[pairId] = dto.pairId
            it[userId] = dto.userId
            it[right] = dto.right
            it[left] = dto.left
            it[status] = dto.status
        }.let { getById(it[MatchingPairAnswersTable.id]) }
    }

    fun update(id: Int, dto: MatchingPairAnswerUpdateDto) = transaction {
        MatchingPairAnswersTable.update({ MatchingPairAnswersTable.id eq id }) { updateStatement ->
            dto.pairId?.let { updateStatement[MatchingPairAnswersTable.pairId] = it }
            dto.userId?.let { updateStatement[MatchingPairAnswersTable.userId] = it }
            dto.right?.let { updateStatement[MatchingPairAnswersTable.right] = it }
            dto.left?.let { updateStatement[MatchingPairAnswersTable.left] = it }
            dto.status?.let { updateStatement[MatchingPairAnswersTable.status] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        MatchingPairAnswersTable.deleteWhere { MatchingPairAnswersTable.id eq id } > 0
    }
}