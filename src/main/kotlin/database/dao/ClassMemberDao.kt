package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.ClassMemberTable
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.models.dto.users.User
import com.fathersprophets.backend.models.response.classes.ClassMemberResponse
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

    fun addMember(classId: Int, userId: Int, isTeacher: Boolean, image: String?) = transaction {
        ClassMemberTable.insert {
            it[ClassMemberTable.classId] = classId
            it[ClassMemberTable.userId] = userId
            it[ClassMemberTable.teacher] = isTeacher
            it[ClassMemberTable.image] = image
        } get ClassMemberTable.id
    }

    fun updateMember(id: Int, isTeacher: Boolean?, image: String?) = transaction {
        ClassMemberTable.update({ ClassMemberTable.id eq id }) {
            isTeacher?.let { t -> it[teacher] = t }
            image?.let { img -> it[ClassMemberTable.image] = img }
        }
    }

    fun deleteMember(id: Int) = transaction {
        ClassMemberTable.deleteWhere { ClassMemberTable.id eq id }
    }
}
