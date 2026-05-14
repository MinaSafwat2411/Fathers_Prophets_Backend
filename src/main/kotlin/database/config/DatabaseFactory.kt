package com.fathersprophets.backend.database.config

import com.fathersprophets.backend.database.tables.AttendanceTable
import com.fathersprophets.backend.database.tables.ClassMemberTable
import com.fathersprophets.backend.database.tables.ClassesTable
import com.fathersprophets.backend.database.tables.CommentsTable
import com.fathersprophets.backend.database.tables.ParentsTable
import com.fathersprophets.backend.database.tables.SessionTable
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.database.tables.VersionsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {
    fun init() {
        Database.connect(
            url = "jdbc:postgresql://ep-dawn-feather-amx09fds-pooler.c-5.us-east-1.aws.neon.tech/fathers-prophets?sslmode=require",
            driver = "org.postgresql.Driver",
            user = "admin",
            password = "npg_dAu8M9xUKjZr"
        )


        transaction {
            SchemaUtils.create(
                UsersTable,
                ClassesTable,
                CommentsTable,
                ClassMemberTable,
                VersionsTable,
                ParentsTable,
                SessionTable,
                AttendanceTable
            )
        }
    }
}