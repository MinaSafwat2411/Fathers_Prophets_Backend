package com.fathersprophets.backend.database.dao.classes

import com.fathersprophets.backend.database.tables.ClassesTable
import com.fathersprophets.backend.models.dto.ClassDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ClassDao {
    private fun resultRowToClass(row: ResultRow) = ClassDto(
        id = row[ClassesTable.id],
        name = row[ClassesTable.name],
        image = row[ClassesTable.image]
    )

    fun findAll() = transaction {
        ClassesTable.selectAll().map { resultRowToClass(it) }
    }

    fun findById(classId : Int) = transaction {
        ClassesTable.selectAll().where { ClassesTable.id eq classId }
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
        } > 0
    }

    fun deleteClass(classId: Int) = transaction {
        ClassesTable.deleteWhere {
            ClassesTable.id eq classId
        } > 0
    }

}