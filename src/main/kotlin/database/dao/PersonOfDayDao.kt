package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.PersonOfDayTable
import com.fathersprophets.backend.models.dto.PersonOfDayDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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
        } get PersonOfDayTable.id
    }

    fun getPersonOfDayById(id: Int) = transaction {
        PersonOfDayTable.select { PersonOfDayTable.id eq id }
            .map { rowToPersonOfDay(it) }
            .singleOrNull()
    }

    fun getPersonOfDayByDate(date: java.time.LocalDate) = transaction {
        PersonOfDayTable.select { PersonOfDayTable.date eq date }
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
        }
    }

    fun deletePersonOfDay(dto: PersonOfDayDto) = transaction {
        PersonOfDayTable.deleteWhere { PersonOfDayTable.id eq dto.id  }
    }
}