package com.fathersprophets.backend.database.tables.quiz

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

class QuizDao : CrudDao<QuizDto, QuizCreateDto, QuizUpdateDto> {

    private fun ResultRow.toDto() = QuizDto(
        id = this[QuizTable.id],
        number = this[QuizTable.number],
        startAt = this[QuizTable.startAt].toString(),
        endAt = this[QuizTable.endAt].toString(),
        title = this[QuizTable.title],
        familyId = this[QuizTable.familyId]
    )

    override fun getAll() = transaction {
        QuizTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        QuizTable.selectAll()
            .where { QuizTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByNumber(number: Int) = transaction {
        QuizTable.selectAll()
            .where { QuizTable.number eq number }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByFamilyId(familyId: Int) = transaction {
        QuizTable.selectAll()
            .where { QuizTable.familyId eq familyId }
            .map { it.toDto() }
    }

    override fun create(dto: QuizCreateDto) = transaction {
        QuizTable.insert {
            it[number] = dto.number
            it[startAt] = Instant.parse(dto.startAt)
            it[endAt] = Instant.parse(dto.endAt)
            it[title] = dto.title
            it[familyId] = dto.familyId
        }.let { getById(it[QuizTable.id]) }
    }

    override fun update(id: Int, dto: QuizUpdateDto) = transaction {
        QuizTable.update({ QuizTable.id eq id }) { updateStatement ->
            dto.number?.let { updateStatement[QuizTable.number] = it }
            dto.startAt?.let { updateStatement[QuizTable.startAt] = Instant.parse(it) }
            dto.endAt?.let { updateStatement[QuizTable.endAt] = Instant.parse(it) }
            dto.title?.let { updateStatement[QuizTable.title] = it }
            dto.familyId?.let { updateStatement[QuizTable.familyId] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        QuizTable.deleteWhere { QuizTable.id eq id } > 0
    }
}