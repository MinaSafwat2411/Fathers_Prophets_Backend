package com.fathersprophets.backend.modules.chat

import com.fathersprophets.backend.base.CrudDao
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

class AnonymousChatDao : CrudDao<AnonymousChatDto, AnonymousChatCreateDto, AnonymousChatUpdateDto> {

    private fun ResultRow.toDto() = AnonymousChatDto(
        id = this[AnonymousChatsTable.id],
        sender = this[AnonymousChatsTable.sender],
        receiver = this[AnonymousChatsTable.receiver],
        lastMessage = this[AnonymousChatsTable.lastMessage],
        createdAt = this[AnonymousChatsTable.createdAt].toString(),
        updatedAt = this[AnonymousChatsTable.updatedAt].toString()
    )

    override fun getAll() = transaction {
        AnonymousChatsTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        AnonymousChatsTable.selectAll()
            .where { AnonymousChatsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }


    override fun create(dto: AnonymousChatCreateDto) = transaction {
        AnonymousChatsTable.insert {
            it[sender] = dto.sender
            it[receiver] = dto.receiver
            it[lastMessage] = dto.lastMessage
        }.let { getById(it[AnonymousChatsTable.id]) }
    }

    override fun update(id: Int, dto: AnonymousChatUpdateDto) = transaction {
        AnonymousChatsTable.update({ AnonymousChatsTable.id eq id }) { updateStatement ->
            dto.lastMessage?.let {
                updateStatement[AnonymousChatsTable.lastMessage] = it
                updateStatement[AnonymousChatsTable.updatedAt] = Instant.now()
            }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        AnonymousChatsTable.deleteWhere { AnonymousChatsTable.id eq id } > 0
    }

    fun block(id: Int, block: Boolean) = transaction {
        AnonymousChatsTable.update({ AnonymousChatsTable.id eq id }) { updateStatement ->
            updateStatement[AnonymousChatsTable.isBlocked] = block
        }.let { getById(id) }
    }
}