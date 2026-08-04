package com.fathersprophets.backend.modules.otp

import com.fathersprophets.backend.database.enums.OtpType
import com.fathersprophets.backend.modules.user.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object OtpTable : Table("otp") {
    val id = integer("id").autoIncrement()
    val userId = reference("user_id", UsersTable.id, onDelete = ReferenceOption.CASCADE).index("idx_otp_user_id")
    val type = customEnumeration(
        "type",
        "otp_type",
        { value -> OtpType.valueOf(value as String) },
        { PGobject().apply { type = "otp_type"; value = it.name } }
    )
    val otpCode = varchar("otp_code", 10).nullable()
    val otpExpiresAt = timestamp("otp_expires_at").nullable()
    val resetTransactionId = varchar("reset_transaction_id", 64).nullable().uniqueIndex()
    val resetVerifyToken = varchar("reset_verify_token", 64).nullable().uniqueIndex()
    val resetVerifyTokenExpiresAt = timestamp("reset_verify_token_expires_at").nullable()

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex("otp_user_type_unique", userId, type)
        TransactionManager.current().exec(
            """
                DO $$ BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'otp_type') THEN
                        CREATE TYPE otp_type AS ENUM (
                            'Email', 'Phone'
                        );
                    END IF;
                END $$;
            """.trimIndent()
        )
    }
}
