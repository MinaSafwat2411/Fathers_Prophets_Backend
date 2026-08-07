package com.fathersprophets.backend.modules.matchingpair

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MatchingPairDao : CrudDao<MatchingPairDto, MatchingPairCreateDto, MatchingPairUpdateDto> {

    private fun ResultRow.toDto() = MatchingPairDto(
        id = this[MatchingPairTable.id],
        title = this[MatchingPairTable.title]
    )

    override fun getAll() = transaction {
        MatchingPairTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        MatchingPairTable.selectAll()
            .where { MatchingPairTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: MatchingPairCreateDto) = transaction {
        MatchingPairTable.insert {
            it[title] = dto.title
        }.let { getById(it[MatchingPairTable.id]) }
    }

    override fun update(id: Int, dto: MatchingPairUpdateDto) = transaction {
        MatchingPairTable.update({ MatchingPairTable.id eq id }) { updateStatement ->
            dto.title?.let { updateStatement[MatchingPairTable.title] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        MatchingPairTable.deleteWhere { MatchingPairTable.id eq id } > 0
    }
}