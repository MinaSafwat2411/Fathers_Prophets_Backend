package com.fathersprophets.backend.modules.session

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class SessionDao {

    private fun ResultRow.toDto() = SessionDto(
        id = this[SessionTable.id],
        dateTime = this[SessionTable.dateTime].toString(),
        createdAt = this[SessionTable.createdAt].toString(),
        familyId = this[SessionTable.familyId]
    )

    fun getAll() = transaction {
        SessionTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        SessionTable.selectAll()
            .where { SessionTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByFamilyId(familyId: Int) = transaction {
        SessionTable.selectAll()
            .where { SessionTable.familyId eq familyId }
            .map { it.toDto() }
    }

    fun create(dto: SessionCreateDto) = transaction {
        SessionTable.insert {
            it[dateTime] = LocalDateTime.parse(dto.dateTime)
            it[familyId] = dto.familyId
        }.let { getById(it[SessionTable.id]) }
    }

    fun update(id: Int, dto: SessionUpdateDto) = transaction {
        SessionTable.update({ SessionTable.id eq id }) { updateStatement ->
            dto.dateTime?.let { updateStatement[SessionTable.dateTime] = LocalDateTime.parse(it) }
            dto.familyId?.let { updateStatement[SessionTable.familyId] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        SessionTable.deleteWhere { SessionTable.id eq id } > 0
    }
}