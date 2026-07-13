package com.fathersprophets.backend.database.dao.person

import com.fathersprophets.backend.database.tables.person.PersonType
import com.fathersprophets.backend.database.tables.person.PersonsTable
import com.fathersprophets.backend.models.dto.PersonDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class PersonDao {
    private fun rowToPerson(row: ResultRow) = PersonDto(
        id = row[PersonsTable.id],
        name = row[PersonsTable.name],
        nickname = row[PersonsTable.nickname],
        shortStory = row[PersonsTable.shortStory],
        fullStory = row[PersonsTable.fullStory],
        image = row[PersonsTable.image],
        type = row[PersonsTable.type]
    )

    fun addPerson(personDto: PersonDto) = transaction {
        PersonsTable.insert {
            it[name] = personDto.name
            it[nickname] = personDto.nickname
            it[shortStory] = personDto.shortStory
            it[fullStory] = personDto.fullStory
            it[image] = personDto.image
            it[type] = personDto.type
        }.let { getPersonById(it[PersonsTable.id]) }
    }

    fun getPersonById(personId : Int) =  transaction {
        PersonsTable.select { PersonsTable.id eq personId}
            .map { rowToPerson(it) }
            .singleOrNull()
    }

    fun getPersonsByType(type : PersonType) = transaction {
        PersonsTable.select { PersonsTable.type eq type }
            .map { rowToPerson(it) }
    }

    fun getAllPersons() = transaction {
        PersonsTable.selectAll()
            .map { rowToPerson(it) }
    }

    fun deletePerson(id: Int) = transaction {
        PersonsTable.deleteWhere { PersonsTable.id eq id } > 0
    }

    fun updatePerson(personDto: PersonDto) = transaction {
        PersonsTable.update({ PersonsTable.id eq personDto.id }) {
            it[name] = personDto.name
            it[nickname] = personDto.nickname
            it[shortStory] = personDto.shortStory
            it[fullStory] = personDto.fullStory
            it[image] = personDto.image
            it[type] = personDto.type
        }.let { getPersonById(personDto.id) }
    }
}