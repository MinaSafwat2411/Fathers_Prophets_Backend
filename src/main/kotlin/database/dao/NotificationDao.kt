package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.NotificationsTable
import com.fathersprophets.backend.database.dto.notification.NotificationDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class NotificationDao {

    private fun rowToDto(row: ResultRow) = NotificationDto(
        id = row[NotificationsTable.id],
        type = row[NotificationsTable.type],
        title = row[NotificationsTable.title],
        referenceId = row[NotificationsTable.referenceId],
        description = row[NotificationsTable.description],
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

    fun create(dto: NotificationDto) = transaction {
        NotificationsTable.insert {
            it[type] = dto.type
            it[title] = dto.title
            it[referenceId] = dto.referenceId
            it[description] = dto.description
            it[createdAt] = dto.createdAt
        }.let { findById(it[NotificationsTable.id]) }
    }

    fun deleteByEventId(eventId: Int) = transaction {
        NotificationsTable.deleteWhere { NotificationsTable.eventId eq eventId } > 0
    }
}