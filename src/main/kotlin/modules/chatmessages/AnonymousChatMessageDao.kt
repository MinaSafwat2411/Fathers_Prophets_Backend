package com.fathersprophets.backend.modules.chatmessages

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class AnonymousChatMessageDao {

    private fun ResultRow.toDto() = AnonymousChatMessageDto(
        id = this[AnonymousChatMessagesTable.id],
        chatId = this[AnonymousChatMessagesTable.chatId],
        message = this[AnonymousChatMessagesTable.message],
        isRead = this[AnonymousChatMessagesTable.isRead],
        createdAt = this[AnonymousChatMessagesTable.createdAt].toString()
    )

    fun getAll() = transaction {
        AnonymousChatMessagesTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        AnonymousChatMessagesTable.selectAll()
            .where { AnonymousChatMessagesTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByChatId(chatId: Int) = transaction {
        AnonymousChatMessagesTable.selectAll()
            .where { AnonymousChatMessagesTable.chatId eq chatId }
            .map { it.toDto() }
    }

    fun create(dto: AnonymousChatMessageCreateDto) = transaction {
        AnonymousChatMessagesTable.insert {
            it[chatId] = dto.chatId
            it[message] = dto.message
        }.let { getById(it[AnonymousChatMessagesTable.id]) }
    }

    fun update(id: Int, dto: AnonymousChatMessageUpdateDto) = transaction {
        AnonymousChatMessagesTable.update({ AnonymousChatMessagesTable.id eq id }) { updateStatement ->
            dto.message?.let { updateStatement[AnonymousChatMessagesTable.message] = it }
            dto.isRead?.let { updateStatement[AnonymousChatMessagesTable.isRead] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        AnonymousChatMessagesTable.deleteWhere { AnonymousChatMessagesTable.id eq id } > 0
    }
}