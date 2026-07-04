package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.AnonymousChatMessagesTable
import com.fathersprophets.backend.models.dto.AnonymousChatMessageDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class AnonymousChatMessageDao {

    private fun rowToDto(row: ResultRow) = AnonymousChatMessageDto(
        id = row[AnonymousChatMessagesTable.id],
        chatId = row[AnonymousChatMessagesTable.chatId],
        memberId = row[AnonymousChatMessagesTable.memberId],
        servantId = row[AnonymousChatMessagesTable.servantId],
        message = row[AnonymousChatMessagesTable.message],
        isRead = row[AnonymousChatMessagesTable.isRead],
        createdAt = row[AnonymousChatMessagesTable.createdAt].toString()
    )

    fun findAll() = transaction {
        AnonymousChatMessagesTable.selectAll().map { rowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        AnonymousChatMessagesTable.selectAll().where { AnonymousChatMessagesTable.id eq id }
            .singleOrNull()?.let { rowToDto(it) }
    }

    fun findByChatId(chatId: Int) = transaction {
        AnonymousChatMessagesTable.selectAll().where { AnonymousChatMessagesTable.chatId eq chatId }
            .orderBy(AnonymousChatMessagesTable.createdAt)
            .map { rowToDto(it) }
    }

    fun create(dto: AnonymousChatMessageDto) = transaction {
        AnonymousChatMessagesTable.insert {
            it[chatId] = dto.chatId
            it[memberId] = dto.memberId
            it[servantId] = dto.servantId
            it[message] = dto.message
            it[isRead] = dto.isRead
        } get AnonymousChatMessagesTable.id
    }

    fun update(dto: AnonymousChatMessageDto) = transaction {
        AnonymousChatMessagesTable.update({ AnonymousChatMessagesTable.id eq dto.id }) {
            it[isRead] = dto.isRead
        } > 0
    }

    fun delete(dto: AnonymousChatMessageDto) = transaction {
        AnonymousChatMessagesTable.deleteWhere { AnonymousChatMessagesTable.id eq dto.id } > 0
    }
}