package com.fathersprophets.backend.database.dao

import com.fathersprophets.backend.database.tables.GuessPersonTable
import com.fathersprophets.backend.models.dto.GuessPersonQuestionDto
import com.fathersprophets.backend.models.guessperson.GuessPersonChoice
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class GuessPersonQuestionDao {

    private fun resultRowToDto(row: ResultRow) = GuessPersonQuestionDto(
        id = row[GuessPersonTable.id],
        question = row[GuessPersonTable.question],
        correctPersonId = row[GuessPersonTable.correctPersonId],
        difficulty = row[GuessPersonTable.difficulty],
        first = Json.decodeFromString<GuessPersonChoice>(row[GuessPersonTable.first]),
        second = Json.decodeFromString<GuessPersonChoice>(row[GuessPersonTable.second]),
        third = Json.decodeFromString<GuessPersonChoice>(row[GuessPersonTable.third]),
        fourth = Json.decodeFromString<GuessPersonChoice>(row[GuessPersonTable.fourth]),
        correctAnswer = row[GuessPersonTable.correctAnswer]
    )

    fun findAll() = transaction {
        GuessPersonTable.selectAll().map { resultRowToDto(it) }
    }

    fun findById(id: Int) = transaction {
        GuessPersonTable.selectAll().where { GuessPersonTable.id eq id }
            .singleOrNull()?.let { resultRowToDto(it) }
    }

    fun create(dto: GuessPersonQuestionDto) = transaction {
        GuessPersonTable.insert {
            it[question] = dto.question
            it[correctPersonId] = dto.correctPersonId
            it[difficulty] = dto.difficulty
            it[first] = Json.encodeToString(dto.first)
            it[second] = Json.encodeToString(dto.second)
            it[third] = Json.encodeToString(dto.third)
            it[fourth] = Json.encodeToString(dto.fourth)
            it[correctAnswer] = dto.correctAnswer
        } get GuessPersonTable.id
    }

    fun update(dto: GuessPersonQuestionDto) = transaction {
        GuessPersonTable.update({ GuessPersonTable.id eq dto.id }) {
            it[question] = dto.question
            it[correctPersonId] = dto.correctPersonId
            it[difficulty] = dto.difficulty
            it[first] = Json.encodeToString(dto.first)
            it[second] = Json.encodeToString(dto.second)
            it[third] = Json.encodeToString(dto.third)
            it[fourth] = Json.encodeToString(dto.fourth)
            it[correctAnswer] = dto.correctAnswer
        } > 0
    }

    fun delete(dto: GuessPersonQuestionDto) = transaction {
        GuessPersonTable.deleteWhere { GuessPersonTable.id eq dto.id } > 0
    }
}