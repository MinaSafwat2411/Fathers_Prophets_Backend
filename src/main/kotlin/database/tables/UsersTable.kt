package com.fathersprophets.backend.database.tables

import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object UsersTable : Table("users") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val username = varchar("username", 255).uniqueIndex()
    val email = varchar("email", 255).nullable().uniqueIndex()
    val phone = varchar("phone", 50).nullable()
    val address = varchar("address", 255).nullable()
    val birthDate = date("birth_date").nullable()
    val fatherName = varchar("father_name", 255).nullable()
    val isShams = bool("is_shams").nullable()
    val profile = varchar("profile", 255).nullable()
    val isReviewed = bool("is_reviewed").nullable()
    val role = varchar("role", 255)
    val fcmToken = varchar("fcm_token", 512).nullable()
    val classId = integer("class_id").references(ClassesTable.id, onDelete = ReferenceOption.SET_NULL).nullable()
    val chats = varchar("chats", 255).nullable()
    val memberId = varchar("member_id", 100).nullable()
    val skipMembership = bool("skip_membership").nullable()
    val comments = varchar("comments", 255).nullable()
    val passwordHash = varchar("password_hash", 255)
    val token = varchar("token", 512).nullable()
    val refreshToken = varchar("refresh_token", 512).nullable()

    override val primaryKey = PrimaryKey(id)
}