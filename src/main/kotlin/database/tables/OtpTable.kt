package com.fathersprophets.backend.database.tables

import com.fathersprophets.backend.database.enums.OtpType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.postgresql.util.PGobject

object OtpTable : Table("otp") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").index("idx_otp_user_id")
    val type = customEnumeration(
        "type",
        "otp_type",
        { value -> OtpType.valueOf(value as String) },
        { PGobject().apply { type = "otp_type"; value = it.name.lowercase() } }
    )
    val otpCode = varchar("otp_code", 10).nullable()
    val otpExpiresAt = timestamp("otp_expires_at").nullable()
    val resetTransactionId = varchar("reset_transaction_id", 64).nullable().uniqueIndex()
    val resetVerifyToken = varchar("reset_verify_token", 64).nullable().uniqueIndex()
    val resetVerifyTokenExpiresAt = timestamp("reset_verify_token_expires_at").nullable()

    override val primaryKey = PrimaryKey(id)
}