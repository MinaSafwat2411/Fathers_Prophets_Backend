package com.fathersprophets.backend.modules.guessperson

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class GuessPersonDao {

    private fun ResultRow.toDto() = GuessPersonDto(
        id = this[GuessPersonTable.id],
        question = this[GuessPersonTable.question],
        correctPersonId = this[GuessPersonTable.correctPersonId],
        difficulty = this[GuessPersonTable.difficulty],
        first = this[GuessPersonTable.first],
        second = this[GuessPersonTable.second],
        third = this[GuessPersonTable.third],
        fourth = this[GuessPersonTable.fourth],
        correctAnswer = this[GuessPersonTable.correctAnswer]
    )

    fun getAll() = transaction {
        GuessPersonTable.selectAll().map { it.toDto() }
    }

    fun getById(id: Int) = transaction {
        GuessPersonTable.selectAll()
            .where { GuessPersonTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByDifficulty(difficulty: com.fathersprophets.backend.database.enums.DifficultyType) = transaction {
        GuessPersonTable.selectAll()
            .where { GuessPersonTable.difficulty eq difficulty }
            .map { it.toDto() }
    }

    fun create(dto: GuessPersonCreateDto) = transaction {
        GuessPersonTable.insert {
            it[question] = dto.question
            it[correctPersonId] = dto.correctPersonId
            it[difficulty] = dto.difficulty
            it[first] = dto.first
            it[second] = dto.second
            it[third] = dto.third
            it[fourth] = dto.fourth
            it[correctAnswer] = dto.correctAnswer
        }.let { getById(it[GuessPersonTable.id]) }
    }

    fun update(id: Int, dto: GuessPersonUpdateDto) = transaction {
        GuessPersonTable.update({ GuessPersonTable.id eq id }) { updateStatement ->
            dto.question?.let { updateStatement[GuessPersonTable.question] = it }
            dto.correctPersonId?.let { updateStatement[GuessPersonTable.correctPersonId] = it }
            dto.difficulty?.let { updateStatement[GuessPersonTable.difficulty] = it }
            dto.first?.let { updateStatement[GuessPersonTable.first] = it }
            dto.second?.let { updateStatement[GuessPersonTable.second] = it }
            dto.third?.let { updateStatement[GuessPersonTable.third] = it }
            dto.fourth?.let { updateStatement[GuessPersonTable.fourth] = it }
            dto.correctAnswer?.let { updateStatement[GuessPersonTable.correctAnswer] = it }
        }.let { getById(id) }
    }

    fun delete(id: Int) = transaction {
        GuessPersonTable.deleteWhere { GuessPersonTable.id eq id } > 0
    }
}