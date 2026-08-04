package com.fathersprophets.backend.modules.matchingpair

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MatchingPairDao {

    private fun ResultRow.toDto() = MatchingPairDto(
        id = this[MatchingPairTable.id],
        title = this[MatchingPairTable.title]
    )

    fun getAll() = transaction {
        MatchingPairTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        MatchingPairTable.selectAll()
            .where { MatchingPairTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: MatchingPairCreateDto) = transaction {
        MatchingPairTable.insert {
            it[title] = dto.title
        }.let { getById(it[MatchingPairTable.id]) }
    }

    fun update(id: Int, dto: MatchingPairUpdateDto) = transaction {
        MatchingPairTable.update({ MatchingPairTable.id eq id }) { updateStatement ->
            dto.title?.let { updateStatement[MatchingPairTable.title] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        MatchingPairTable.deleteWhere { MatchingPairTable.id eq id } > 0
    }
}