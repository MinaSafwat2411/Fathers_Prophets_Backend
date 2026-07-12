package com.fathersprophets.backend.database.tables.users

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

enum class UserRole {
    member,
    admin,
    superadmin,
    football,
    teacher,
    volleyball,
    chess,
    pingPong,
    pray,
    praise,
    doctrine,
    bible,
    ritual,
    coptic,
    choir,
    mahrgan,
    odas,
    shmas,
    sports,
    spiritual,
    melodies,
    games,
    quiz,
    parent
}

object UsersTable : Table("users") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val username = varchar("username", 255).uniqueIndex()
    val email = varchar("email", 255).nullable().uniqueIndex()
    val phone = varchar("phone", 50).nullable()
    val address = varchar("address", 255).nullable()
    val birthDate = varchar("birth_date", 255).nullable()
    val fatherName = varchar("father_name", 255).nullable()
    val isShams = bool("is_shams").nullable()
    val profile = varchar("profile", 255).nullable()
    val isReviewed = bool("is_reviewed").nullable().index("idx_users_is_reviewed")
    val role = customEnumeration(
        "role",
        "user_role",
        { value -> UserRole.valueOf(value as String) },
        { PGobject().apply { type = "user_role"; value = it.name } }
    ).index("idx_users_role")
    val fcmToken = varchar("fcm_token", 512).nullable()
    val memberId = varchar("member_id", 100).nullable()
    val skipMembership = bool("skip_membership").nullable()
    val passwordHash = varchar("password_hash", 255)
    val token = varchar("token", 512).nullable()
    val refreshToken = varchar("refresh_token", 512).nullable()

    override val primaryKey = PrimaryKey(id)
}