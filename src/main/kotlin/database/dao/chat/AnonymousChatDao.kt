package com.fathersprophets.backend.database.dao.chat

import com.fathersprophets.backend.database.tables.chat.AnonymousChatsTable
import com.fathersprophets.backend.models.dto.AnonymousChatDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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

    fun findChatByMemberId(memberId: Int) = transaction {
        AnonymousChatsTable.selectAll().where { AnonymousChatsTable.memberId eq memberId }
            .map{ rowToDto(it) }
    }

    fun findChatByServantId(servantId: Int) = transaction {
        AnonymousChatsTable.selectAll().where { AnonymousChatsTable.servantId eq servantId }
            .map{ rowToDto(it) }
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
        }.let { findById(it[AnonymousChatsTable.id]) }
    }

    fun update(dto: AnonymousChatDto) = transaction {
        AnonymousChatsTable.update({ AnonymousChatsTable.id eq dto.id }) {
            it[lastMessage] = dto.lastMessage
        }.let { findById(dto.id) }
    }

    fun delete(id: Int) = transaction {
        AnonymousChatsTable.deleteWhere { AnonymousChatsTable.id eq id } > 0
    }
}