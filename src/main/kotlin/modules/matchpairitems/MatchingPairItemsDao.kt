package com.fathersprophets.backend.modules.matchpairitems

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MatchingPairItemsDao {

    private fun ResultRow.toDto() = MatchingPairItemDto(
        id = this[MatchingPairItemsTable.id],
        pairId = this[MatchingPairItemsTable.pairId],
        right = this[MatchingPairItemsTable.right],
        left = this[MatchingPairItemsTable.left]
    )

    fun getAll() = transaction {
        MatchingPairItemsTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        MatchingPairItemsTable.selectAll()
            .where { MatchingPairItemsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByPairId(pairId: Int) = transaction {
        MatchingPairItemsTable.selectAll()
            .where { MatchingPairItemsTable.pairId eq pairId }
            .map { it.toDto() }
    }

    fun create(dto: MatchingPairItemCreateDto) = transaction {
        MatchingPairItemsTable.insert {
            it[pairId] = dto.pairId
            it[right] = dto.right
            it[left] = dto.left
        }.let { getById(it[MatchingPairItemsTable.id]) }
    }

    fun update(id: Int, dto: MatchingPairItemUpdateDto) = transaction {
        MatchingPairItemsTable.update({ MatchingPairItemsTable.id eq id }) { updateStatement ->
            dto.pairId?.let { updateStatement[MatchingPairItemsTable.pairId] = it }
            dto.right?.let { updateStatement[MatchingPairItemsTable.right] = it }
            dto.left?.let { updateStatement[MatchingPairItemsTable.left] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        MatchingPairItemsTable.deleteWhere { MatchingPairItemsTable.id eq id } > 0
    }
}