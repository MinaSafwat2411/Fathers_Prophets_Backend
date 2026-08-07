package com.fathersprophets.backend.modules.chatmessages

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class AnonymousChatMessageDao : CrudDao<AnonymousChatMessageDto, AnonymousChatMessageCreateDto, AnonymousChatMessageUpdateDto> {

    private fun ResultRow.toDto() = AnonymousChatMessageDto(
        id = this[AnonymousChatMessagesTable.id],
        chatId = this[AnonymousChatMessagesTable.chatId],
        message = this[AnonymousChatMessagesTable.message],
        isRead = this[AnonymousChatMessagesTable.isRead],
        createdAt = this[AnonymousChatMessagesTable.createdAt].toString()
    )

    override fun getAll() = transaction {
        AnonymousChatMessagesTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
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

    override fun create(dto: AnonymousChatMessageCreateDto) = transaction {
        AnonymousChatMessagesTable.insert {
            it[chatId] = dto.chatId
            it[message] = dto.message
        }.let { getById(it[AnonymousChatMessagesTable.id]) }
    }

    override fun update(id: Int, dto: AnonymousChatMessageUpdateDto) = transaction {
        AnonymousChatMessagesTable.update({ AnonymousChatMessagesTable.id eq id }) { updateStatement ->
            dto.message?.let { updateStatement[AnonymousChatMessagesTable.message] = it }
            dto.isRead?.let { updateStatement[AnonymousChatMessagesTable.isRead] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        AnonymousChatMessagesTable.deleteWhere { AnonymousChatMessagesTable.id eq id } > 0
    }
}