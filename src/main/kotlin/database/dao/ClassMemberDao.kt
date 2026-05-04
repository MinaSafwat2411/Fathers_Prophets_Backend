package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.ClassMemberTable
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.models.request.classmember.AddClassMemberRequest
import com.fathersprophets.backend.models.request.classmember.UpdateClassMemberRequest
import com.fathersprophets.backend.models.dto.classes.ClassMemberResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ClassMemberDao {
    private fun resultRowToClassMember(row: ResultRow) = ClassMemberResponse(
        id = row[ClassMemberTable.id],
        name = row[UsersTable.name],
        image = row[ClassMemberTable.image],
        isTeacher = row[ClassMemberTable.teacher],
        classId = row[ClassMemberTable.classId]
    )

    fun findMemberClass(classId: Int) = transaction {
        (ClassMemberTable innerJoin UsersTable)
            .selectAll()
            .where { ClassMemberTable.classId eq classId }
            .map { resultRowToClassMember(it) }
    }

    fun addMember(addClassMemberRequest: AddClassMemberRequest) = transaction {
        ClassMemberTable.insert {
            it[ClassMemberTable.name] = addClassMemberRequest.name
            it[ClassMemberTable.classId] = addClassMemberRequest.classId
            it[ClassMemberTable.userId] = addClassMemberRequest.userId
            it[ClassMemberTable.teacher] = addClassMemberRequest.isTeacher
            it[ClassMemberTable.image] = addClassMemberRequest.image
        } get ClassMemberTable.id
    }

    fun updateMember(id: Int, updateClassMemberRequest: UpdateClassMemberRequest) = transaction {
        ClassMemberTable.update({ ClassMemberTable.id eq id }) {
            it[ClassMemberTable.name] = updateClassMemberRequest.name
            it[ClassMemberTable.classId] = updateClassMemberRequest.classId
            it[ClassMemberTable.teacher] = updateClassMemberRequest.isTeacher
            it[ClassMemberTable.image] = updateClassMemberRequest.image
            it[ClassMemberTable.userId] = updateClassMemberRequest.userId
        }
    }

    fun deleteMember(id: Int) = transaction {
        ClassMemberTable.deleteWhere { ClassMemberTable.id eq id }
    }
}
