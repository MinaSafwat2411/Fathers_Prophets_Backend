package com.fathersprophets.backend.modules.chat

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

class AnonymousChatDao {

    private fun ResultRow.toDto() = AnonymousChatDto(
        id = this[AnonymousChatsTable.id],
        memberId = this[AnonymousChatsTable.memberId],
        servantId = this[AnonymousChatsTable.servantId],
        lastMessage = this[AnonymousChatsTable.lastMessage],
        createdAt = this[AnonymousChatsTable.createdAt].toString(),
        updatedAt = this[AnonymousChatsTable.updatedAt].toString()
    )

    fun getAll() = transaction {
        AnonymousChatsTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        AnonymousChatsTable.selectAll()
            .where { AnonymousChatsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByMemberAndServant(memberId: Int, servantId: Int) = transaction {
        AnonymousChatsTable.selectAll()
            .where { (AnonymousChatsTable.memberId eq memberId) and (AnonymousChatsTable.servantId eq servantId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun create(dto: AnonymousChatCreateDto) = transaction {
        AnonymousChatsTable.insert {
            it[memberId] = dto.memberId
            it[servantId] = dto.servantId
            it[lastMessage] = dto.lastMessage
        }.let { getById(it[AnonymousChatsTable.id]) }
    }

    fun update(id: Int, dto: AnonymousChatUpdateDto) = transaction {
        AnonymousChatsTable.update({ AnonymousChatsTable.id eq id }) { updateStatement ->
            dto.lastMessage?.let {
                updateStatement[AnonymousChatsTable.lastMessage] = it
                updateStatement[AnonymousChatsTable.updatedAt] = Instant.now()
            }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        AnonymousChatsTable.deleteWhere { AnonymousChatsTable.id eq id }>0
    }
}