package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.AnonymousChatsTable
import com.fathersprophets.backend.models.dto.AnonymousChatDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class AnonymousChatDao {

    private fun rowToDto(row: ResultRow) = AnonymousChatDto(
        id = row[AnonymousChatsTable.id],
        memberId = row[AnonymousChatsTable.memberId],
        servantId = row[AnonymousChatsTable.servantId],
        lastMessage = row[AnonymousChatsTable.lastMessage],
        createdAt = row[AnonymousChatsTable.createdAt].toString(),
        updatedAt = row[AnonymousChatsTable.updatedAt].toString()
    )

    fun findAll() = transaction {
        AnonymousChatsTable.selectAll().map { rowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        AnonymousChatsTable.selectAll().where { AnonymousChatsTable.id eq id }
            .singleOrNull()?.let { rowToDto(it) }
    }

    fun create(dto: AnonymousChatDto) = transaction {
        AnonymousChatsTable.insert {
            it[memberId] = dto.memberId
            it[servantId] = dto.servantId
            it[lastMessage] = dto.lastMessage
        } get AnonymousChatsTable.id
    }

    fun update(dto: AnonymousChatDto) = transaction {
        AnonymousChatsTable.update({ AnonymousChatsTable.id eq dto.id }) {
            it[lastMessage] = dto.lastMessage
        } > 0
    }

    fun delete(dto: AnonymousChatDto) = transaction {
        AnonymousChatsTable.deleteWhere { AnonymousChatsTable.id eq dto.id } > 0
    }
}