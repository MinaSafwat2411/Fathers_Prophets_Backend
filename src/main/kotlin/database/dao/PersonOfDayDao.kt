package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.PersonOfDayTable
import com.fathersprophets.backend.models.dto.PersonOfDayDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class PersonOfDayDao {
    private fun rowToPersonOfDay(row: ResultRow) = PersonOfDayDto(
        id = row[PersonOfDayTable.id],
        personId = row[PersonOfDayTable.personId],
        message = row[PersonOfDayTable.message],
        verse = row[PersonOfDayTable.verse],
        date = row[PersonOfDayTable.date]
    )

    fun addPersonOfDay(dto: PersonOfDayDto) = transaction {
        PersonOfDayTable.insert {
            it[personId] = dto.personId
            it[message] = dto.message
            it[verse] = dto.verse
            it[date] = dto.date
        }.let { getPersonOfDayById(it[PersonOfDayTable.id]) }
    }

    fun getPersonOfDayById(id: Int) = transaction {
        PersonOfDayTable.selectAll().where { PersonOfDayTable.id eq id }
            .map { rowToPersonOfDay(it) }
            .singleOrNull()
    }

    fun getPersonOfDayByDate() = transaction {
        PersonOfDayTable.selectAll().where { PersonOfDayTable.date eq LocalDate.now() }
            .map { rowToPersonOfDay(it) }
            .singleOrNull()
    }

    fun getAllPersonsOfDay() = transaction {
        PersonOfDayTable.selectAll()
            .map { rowToPersonOfDay(it) }
    }

    fun updatePersonOfDay(dto: PersonOfDayDto) = transaction {
        PersonOfDayTable.update({ PersonOfDayTable.id eq dto.id }) {
            it[personId] = dto.personId
            it[message] = dto.message
            it[verse] = dto.verse
            it[date] = dto.date
        }.let { getPersonOfDayById(dto.id) }
    }

    fun deletePersonOfDay(id: Int) = transaction {
        PersonOfDayTable.deleteWhere { PersonOfDayTable.id eq id } > 0
    }
}