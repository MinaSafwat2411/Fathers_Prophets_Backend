package com.fathersprophets.backend.modules.notification

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class NotificationDao : CrudDao<NotificationDto, NotificationCreateDto, NotificationUpdateDto> {

    private fun ResultRow.toDto() = NotificationDto(
        id = this[NotificationsTable.id],
        type = this[NotificationsTable.type],
        title = this[NotificationsTable.title],
        description = this[NotificationsTable.description],
        referenceId = this[NotificationsTable.referenceId],
        createdAt = this[NotificationsTable.createdAt].toString()
    )

    override fun getAll() = transaction {
        NotificationsTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        NotificationsTable.selectAll()
            .where { NotificationsTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByType(type: com.fathersprophets.backend.database.enums.NotificationType) = transaction {
        NotificationsTable.selectAll()
            .where { NotificationsTable.type eq type }
            .map { it.toDto() }
    }

    override fun create(dto: NotificationCreateDto) = transaction {
        NotificationsTable.insert {
            it[type] = dto.type
            it[title] = dto.title
            it[description] = dto.description
            it[referenceId] = dto.referenceId
        }.let { getById(it[NotificationsTable.id]) }
    }

    override fun update(id: Int, dto: NotificationUpdateDto) = transaction {
        NotificationsTable.update({ NotificationsTable.id eq id }) { updateStatement ->
            dto.type?.let { updateStatement[NotificationsTable.type] = it }
            dto.title?.let { updateStatement[NotificationsTable.title] = it }
            dto.description?.let { updateStatement[NotificationsTable.description] = it }
            dto.referenceId?.let { updateStatement[NotificationsTable.referenceId] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        NotificationsTable.deleteWhere { NotificationsTable.id eq id } > 0
    }
}