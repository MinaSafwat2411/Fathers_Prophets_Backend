package com.fathersprophets.backend.modules.matchpairitems

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MatchingPairItemsDao : CrudDao<MatchingPairItemDto, MatchingPairItemCreateDto, MatchingPairItemUpdateDto> {

    private fun ResultRow.toDto() = MatchingPairItemDto(
        id = this[MatchingPairItemsTable.id],
        pairId = this[MatchingPairItemsTable.pairId],
        right = this[MatchingPairItemsTable.right],
        left = this[MatchingPairItemsTable.left]
    )

    override fun getAll() = transaction {
        MatchingPairItemsTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
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

    override fun create(dto: MatchingPairItemCreateDto) = transaction {
        MatchingPairItemsTable.insert {
            it[pairId] = dto.pairId
            it[right] = dto.right
            it[left] = dto.left
        }.let { getById(it[MatchingPairItemsTable.id]) }
    }

    override fun update(id: Int, dto: MatchingPairItemUpdateDto) = transaction {
        MatchingPairItemsTable.update({ MatchingPairItemsTable.id eq id }) { updateStatement ->
            dto.pairId?.let { updateStatement[MatchingPairItemsTable.pairId] = it }
            dto.right?.let { updateStatement[MatchingPairItemsTable.right] = it }
            dto.left?.let { updateStatement[MatchingPairItemsTable.left] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        MatchingPairItemsTable.deleteWhere { MatchingPairItemsTable.id eq id } > 0
    }
}