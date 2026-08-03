package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.matchingpair.MatchingPairTable
import com.fathersprophets.backend.models.dto.MatchingPairDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MatchingPairDao {

    private fun resultRowToDto(row: ResultRow) = MatchingPairDto(
        id = row[MatchingPairTable.id],
        personId = row[MatchingPairTable.personId],
        personName = row[MatchingPairTable.personName],
        otherSide = row[MatchingPairTable.otherSide]
    )

    fun findAll() = transaction {
        MatchingPairTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        MatchingPairTable.selectAll().where { MatchingPairTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun create(dto: MatchingPairDto) = transaction {
        MatchingPairTable.insert {
            it[personId] = dto.personId
            it[personName] = dto.personName
            it[otherSide] = dto.otherSide
        }.let { findById(it[MatchingPairTable.id]) }
    }

    fun update(dto: MatchingPairDto) = transaction {
        MatchingPairTable.update({ MatchingPairTable.id eq dto.id }) {
            it[personId] = dto.personId
            it[personName] = dto.personName
            it[otherSide] = dto.otherSide
        }.let { findById(dto.id) }
    }

    fun delete(id: Int) = transaction {
        MatchingPairTable.deleteWhere { MatchingPairTable.id eq id } > 0
    }
}