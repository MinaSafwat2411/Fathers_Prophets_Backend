package com.fathersprophets.backend.modules.escapeegypt

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EscapeEgyptDao : CrudDao<EscapeEgyptDto, EscapeEgyptCreateDto, EscapeEgyptUpdateDto> {

    private fun ResultRow.toDto() = EscapeEgyptDto(
        id = this[EscapeEgyptTable.id],
        title = this[EscapeEgyptTable.title],
        type = this[EscapeEgyptTable.type]
    )

    override fun getAll() = transaction {
        EscapeEgyptTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        EscapeEgyptTable.selectAll()
            .where { EscapeEgyptTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: EscapeEgyptCreateDto) = transaction {
        EscapeEgyptTable.insert {
            it[title] = dto.title
            it[type] = dto.type
        }.let { getById(it[EscapeEgyptTable.id]) }
    }

    override fun update(id: Int, dto: EscapeEgyptUpdateDto) = transaction {
        EscapeEgyptTable.update({ EscapeEgyptTable.id eq id }) { updateStatement ->
            dto.title?.let { updateStatement[EscapeEgyptTable.title] = it }
            dto.type?.let { updateStatement[EscapeEgyptTable.type] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        EscapeEgyptTable.deleteWhere { EscapeEgyptTable.id eq id } > 0
    }
}