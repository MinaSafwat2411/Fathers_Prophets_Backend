package com.fathersprophets.backend.modules.notificationuser

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

class NotificationUserDao : CrudDao<NotificationUserDto, NotificationUserCreateDto, NotificationUserUpdateDto> {

    private fun ResultRow.toDto() = NotificationUserDto(
        id = this[NotificationsUserTable.id],
        notificationId = this[NotificationsUserTable.notificationId],
        userId = this[NotificationsUserTable.userId],
        isRead = this[NotificationsUserTable.isRead],
        readAt = this[NotificationsUserTable.readAt]?.toString()
    )

    override fun getAll() = transaction {
        NotificationsUserTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        NotificationsUserTable.selectAll()
            .where { NotificationsUserTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByUserId(userId: Int) = transaction {
        NotificationsUserTable.selectAll()
            .where { NotificationsUserTable.userId eq userId }
            .map { it.toDto() }
    }

    fun getByNotificationId(notificationId: Int) = transaction {
        NotificationsUserTable.selectAll()
            .where { NotificationsUserTable.notificationId eq notificationId }
            .map { it.toDto() }
    }

    fun getByUserAndNotification(userId: Int, notificationId: Int) = transaction {
        NotificationsUserTable.selectAll()
            .where { (NotificationsUserTable.userId eq userId) and (NotificationsUserTable.notificationId eq notificationId) }
            .map { it.toDto() }
            .singleOrNull()
    }

    override fun create(dto: NotificationUserCreateDto) = transaction {
        NotificationsUserTable.insert {
            it[notificationId] = dto.notificationId
            it[userId] = dto.userId
            it[isRead] = dto.isRead
            it[readAt] = dto.readAt?.let { instantStr -> Instant.parse(instantStr) }
        }.let { getById(it[NotificationsUserTable.id]) }
    }

    override fun update(id: Int, dto: NotificationUserUpdateDto) = transaction {
        NotificationsUserTable.update({ NotificationsUserTable.id eq id }) { updateStatement ->
            dto.notificationId?.let { updateStatement[NotificationsUserTable.notificationId] = it }
            dto.userId?.let { updateStatement[NotificationsUserTable.userId] = it }
            dto.isRead?.let { updateStatement[NotificationsUserTable.isRead] = it }
            dto.readAt?.let { updateStatement[NotificationsUserTable.readAt] = Instant.parse(it) }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        NotificationsUserTable.deleteWhere { NotificationsUserTable.id eq id } > 0
    }
}