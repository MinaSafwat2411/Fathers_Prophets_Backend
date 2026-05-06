package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.ClassMemberTable
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.models.dto.ClassMemberDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ClassMemberDao {
    private fun resultRowToClassMember(row: ResultRow) = ClassMemberDto(
        id = row[ClassMemberTable.id],
        name = row[UsersTable.name],
        image = row[ClassMemberTable.image],
        isTeacher = row[ClassMemberTable.teacher],
        classId = row[ClassMemberTable.classId],
        userId = row[ClassMemberTable.userId]
    )

    fun findMemberClass(classMemberDto: ClassMemberDto) = transaction {
        (ClassMemberTable innerJoin UsersTable)
            .selectAll()
            .where { ClassMemberTable.classId eq classMemberDto.classId }
            .map { resultRowToClassMember(it) }
    }

    fun findById(classMemberDto: ClassMemberDto) = transaction {
        ClassMemberTable.selectAll().where { ClassMemberTable.id eq classMemberDto.id }
            .singleOrNull()?.let { resultRowToClassMember(it) }
    }
    fun addMember(classMemberDto: ClassMemberDto) = transaction {
        ClassMemberTable.insert {
            it[ClassMemberTable.name] = classMemberDto.name
            it[ClassMemberTable.classId] = classMemberDto.classId
            it[ClassMemberTable.userId] = classMemberDto.userId
            it[ClassMemberTable.teacher] = classMemberDto.isTeacher
            it[ClassMemberTable.image] = classMemberDto.image
        } get ClassMemberTable.id
    }

    fun updateMember(classMemberDto: ClassMemberDto) = transaction {
        ClassMemberTable.update({ ClassMemberTable.id eq classMemberDto.id }) {
            it[ClassMemberTable.name] = classMemberDto.name
            it[ClassMemberTable.classId] = classMemberDto.classId
            it[ClassMemberTable.teacher] = classMemberDto.isTeacher
            it[ClassMemberTable.image] = classMemberDto.image
            it[ClassMemberTable.userId] = classMemberDto.userId
        }
    }

    fun deleteMember(classMemberDto: ClassMemberDto) = transaction {
        ClassMemberTable.deleteWhere { ClassMemberTable.id eq classMemberDto.id }
    }
}
