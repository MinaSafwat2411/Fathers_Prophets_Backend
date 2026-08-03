package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.escapeegypt.EscapeEgyptTable
import com.fathersprophets.backend.database.dto.EscapeEgyptDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EscapeEgyptDao {

    private fun resultRowToDto(row: ResultRow) = EscapeEgyptDto(
        id = row[EscapeEgyptTable.id],
        title = row[EscapeEgyptTable.title],
        type = row[EscapeEgyptTable.type]
    )

    fun findAll() = transaction {
        EscapeEgyptTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        EscapeEgyptTable.selectAll().where { EscapeEgyptTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun create(dto: EscapeEgyptDto) = transaction {
        EscapeEgyptTable.insert {
            it[title] = dto.title
            it[type] = dto.type
        }.let { findById(it[EscapeEgyptTable.id]) }
    }

    fun update(dto: EscapeEgyptDto) = transaction {
        EscapeEgyptTable.update({ EscapeEgyptTable.id eq dto.id }) {
            it[title] = dto.title
            it[type] = dto.type
        }.let { findById(dto.id) }
    }

    fun delete(escapeEgyptId: Int) = transaction {
        EscapeEgyptTable.deleteWhere { EscapeEgyptTable.id eq escapeEgyptId } > 0
    }
}