package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.ClassesTable
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.models.dto.Class
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ClassDao {
    private fun resultRowToClass(row: ResultRow) = Class(
        id = row[ClassesTable.id],
        name = row[ClassesTable.name],
        image = row[ClassesTable.image]
    )

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

    fun updateClass(id: Int, data: Map<String, Any?>) = transaction {
        ClassesTable.update({ ClassesTable.id eq id }) {
            data["name"]?.let { name -> it[ClassesTable.name] = name as String }
            data["image"]?.let { image -> it[ClassesTable.image] = image as String }
        }
    }

    fun deleteClass(id: Int) = transaction {
        ClassesTable.deleteWhere {
            ClassesTable.id eq id
        }
    }

    fun getClassMember(classId: Int) = transaction {
        UsersTable.selectAll().where { UsersTable.classId eq classId }
            .map { resultRowToClass(it) }
    }

}
