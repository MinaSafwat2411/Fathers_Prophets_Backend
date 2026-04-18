package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.ClassesTable
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.models.dto.classes.Class
import com.fathersprophets.backend.models.request.classes.UpdateClassRequest
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

    fun updateClass(id: Int,updateClassRequest: UpdateClassRequest) = transaction {
        ClassesTable.update({ ClassesTable.id eq id }) {
            it[name] = updateClassRequest.name
            it[image] = updateClassRequest.image
        }
    }

    fun deleteClass(id: Int) = transaction {
        ClassesTable.deleteWhere {
            ClassesTable.id eq id
        }
    }

}
