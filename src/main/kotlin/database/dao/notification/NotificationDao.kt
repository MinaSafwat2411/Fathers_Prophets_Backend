package com.fathersprophets.backend.database.dao.notification

import com.fathersprophets.backend.database.tables.notification.NotificationsTable
import com.fathersprophets.backend.models.dto.NotificationDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class NotificationDao {

    private fun rowToDto(row: ResultRow) = NotificationDto(
        id = row[NotificationsTable.id],
        eventId = row[NotificationsTable.eventId],
        type = row[NotificationsTable.type],
        title = row[NotificationsTable.title],
        message = row[NotificationsTable.message],
        isRead = row[NotificationsTable.isRead],
        createdAt = row[NotificationsTable.createdAt].toString()
    )

    fun findAll() = transaction {
        NotificationsTable.selectAll()
            .orderBy(NotificationsTable.createdAt, SortOrder.DESC)
            .map { rowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        NotificationsTable.selectAll().where { NotificationsTable.id eq id }
            .singleOrNull()?.let { rowToDto(it) }
    }

    fun findByEventId(eventId: Int) = transaction {
        NotificationsTable.selectAll().where { NotificationsTable.eventId eq eventId }
            .orderBy(NotificationsTable.createdAt, SortOrder.DESC)
            .map { rowToDto(it) }
    }

    fun create(dto: NotificationDto) = transaction {
        NotificationsTable.insert {
            it[eventId] = dto.eventId
            it[type] = dto.type
            it[title] = dto.title
            it[message] = dto.message
        }.let { findById(it[NotificationsTable.id]) }
    }

    fun update(dto: NotificationDto) = transaction {
        NotificationsTable.update({ NotificationsTable.id eq dto.id }) {
            it[isRead] = dto.isRead
        }.let { findById(dto.id) }
    }

    fun delete(id: Int) = transaction {
        NotificationsTable.deleteWhere { NotificationsTable.id eq id } > 0
    }
}