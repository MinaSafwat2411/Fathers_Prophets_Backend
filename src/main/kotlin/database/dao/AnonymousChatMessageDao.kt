package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.chat.AnonymousChatMessagesTable
import com.fathersprophets.backend.models.dto.AnonymousChatMessageDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class AnonymousChatMessageDao {

    private fun rowToDto(row: ResultRow) = AnonymousChatMessageDto(
        id = row[AnonymousChatMessagesTable.id],
        chatId = row[AnonymousChatMessagesTable.chatId],
        memberId = row[AnonymousChatMessagesTable.memberId],
        servantId = row[AnonymousChatMessagesTable.servantId],
        message = row[AnonymousChatMessagesTable.message],
        memberName = row[AnonymousChatMessagesTable.memberName],
        servantName = row[AnonymousChatMessagesTable.servantName],
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

    fun findByChatId(chatId: Int,userId: Int) = transaction {
        AnonymousChatMessagesTable.selectAll()
            .where { AnonymousChatMessagesTable.chatId eq chatId and ((AnonymousChatMessagesTable.memberId eq userId) or (AnonymousChatMessagesTable.servantId eq userId)) }
            .orderBy(AnonymousChatMessagesTable.createdAt)
            .map { rowToDto(it) }
    }

    fun create(dto: AnonymousChatMessageDto) = transaction {
        AnonymousChatMessagesTable.insert {
            it[chatId] = dto.chatId
            it[memberId] = dto.memberId
            it[servantId] = dto.servantId
            it[message] = dto.message
            it[memberName] = dto.memberName
            it[servantName] = dto.servantName
            it[isRead] = dto.isRead
        }.let { findById(it[AnonymousChatMessagesTable.id]) }
    }

    fun delete(id: Int) = transaction {
        AnonymousChatMessagesTable.deleteWhere { AnonymousChatMessagesTable.id eq id } > 0
    }

    fun update(dto: AnonymousChatMessageDto) = transaction {
        AnonymousChatMessagesTable.update({ AnonymousChatMessagesTable.id eq dto.id }) {
            it[message] = dto.message
        }.let { findById(dto.id) }
    }
}