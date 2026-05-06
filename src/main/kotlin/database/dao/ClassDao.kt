package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.ClassesTable
import com.fathersprophets.backend.models.dto.ClassDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ClassDao {
    private fun resultRowToClass(row: ResultRow) = ClassDto(
        id = row[ClassesTable.id],
        name = row[ClassesTable.name],
        image = row[ClassesTable.image]
    )

    fun findAll() = transaction {
        ClassesTable.selectAll().map { resultRowToClass(it) }
    }

    fun findById(classDto: ClassDto) = transaction {
        ClassesTable.selectAll().where { ClassesTable.id eq classDto.id }
            .singleOrNull()?.let { resultRowToClass(it) }
    }

    fun createClass(classDto: ClassDto) = transaction {
        ClassesTable.insert {
            it[name] = classDto.name
            it[image] = classDto.image
        } get ClassesTable.id
    }

    fun updateClass(classDto: ClassDto) = transaction {
        ClassesTable.update({ ClassesTable.id eq classDto.id }) {
            it[name] = classDto.name
            it[image] = classDto.image
        }
    }

    fun deleteClass(classDto: ClassDto) = transaction {
        ClassesTable.deleteWhere {
            ClassesTable.id eq classDto.id
        }
    }

}
