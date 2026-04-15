package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.ClassesTable
import com.fathersprophets.backend.database.tables.UsersTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ClassDao {
    private fun resultRowToClass(row: ResultRow) = row

    fun findAll() = transaction {
        ClassesTable.selectAll().map { resultRowToClass(it) }
    }

    fun findById(id: Int) = transaction {
        ClassesTable.selectAll().where { ClassesTable.id eq id }
            .singleOrNull()?.let { resultRowToClass(it) }
    }

    fun createClass(data: Map<String, Any?>) = transaction {
        ClassesTable.insert {
            it[ClassesTable.name] = data["name"] as String
            it[ClassesTable.image] = data["image"] as String?
        } get ClassesTable.id
    }
    
    fun findClassMember(id: Int) = transaction {
        UsersTable.selectAll().where { UsersTable.classId eq id }
            .map { it }
    }
}
