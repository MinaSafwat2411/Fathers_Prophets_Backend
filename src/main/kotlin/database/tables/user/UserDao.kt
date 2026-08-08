package com.fathersprophets.backend.database.tables.user

import com.fathersprophets.backend.base.CrudDao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class UserDao : CrudDao<UserDto, UserCreateDto, UserUpdateDto> {

    private fun ResultRow.toDto() = UserDto(
        id = this[UsersTable.id],
        fullName = this[UsersTable.fullName],
        firstName = this[UsersTable.firstName],
        lastName = this[UsersTable.lastName],
        username = this[UsersTable.username],
        email = this[UsersTable.email],
        phone = this[UsersTable.phone],
        address = this[UsersTable.address],
        birthDate = this[UsersTable.birthDate]?.toString(),
        fatherConfession = this[UsersTable.fatherConfession],
        fatherPhone = this[UsersTable.fatherPhone],
        motherPhone = this[UsersTable.motherPhone],
        isShams = this[UsersTable.isShams],
        profile = this[UsersTable.profile],
        isReviewed = this[UsersTable.isReviewed],
        isVerified = this[UsersTable.isVerified],
        role = this[UsersTable.role],
        memberId = this[UsersTable.memberId],
        familyId = this[UsersTable.familyId],
        classId = this[UsersTable.classId],
        score = this[UsersTable.score]
    )

    override fun getAll() = transaction {
        UsersTable.selectAll().map { it.toDto() }
    }

    override fun getById(id: Int) = transaction {
        UsersTable.selectAll()
            .where { UsersTable.id eq id }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByUsername(username: String) = transaction {
        UsersTable.selectAll()
            .where { UsersTable.username eq username }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getPasswordHashByUsername(username: String) = transaction {
        UsersTable.selectAll()
            .where { UsersTable.username eq username }
            .map { it[UsersTable.password] }
            .singleOrNull()
    }

    fun getByEmail(email: String) = transaction {
        UsersTable.selectAll()
            .where { UsersTable.email eq email }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByPhone(phone: String) = transaction {
        UsersTable.selectAll()
            .where { UsersTable.phone eq phone }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getByMemberId(memberId: String) = transaction {
        UsersTable.selectAll()
            .where { UsersTable.memberId eq memberId }
            .map { it.toDto() }
            .singleOrNull()
    }

    fun getUsersWithBirthDate() = transaction {
        UsersTable.selectAll()
            .where { UsersTable.birthDate.isNotNull() }
            .map { it.toDto() }
    }

    fun getByFamilyId(familyId: Int) = transaction {
        UsersTable.selectAll()
            .where { UsersTable.familyId eq familyId }
            .map { it.toDto() }
    }

    fun getByClassId(classId: Int) = transaction {
        UsersTable.selectAll()
            .where { UsersTable.classId eq classId }
            .map { it.toDto() }
    }

    override fun create(dto: UserCreateDto) = transaction {
        UsersTable.insert {
            it[firstName] = dto.firstName
            it[lastName] = dto.lastName
            it[fullName] = "${dto.firstName} ${dto.lastName}"
            it[password] = dto.password
            it[username] = dto.username
            it[email] = dto.email
            it[phone] = dto.phone
            it[address] = dto.address
            it[birthDate] = dto.birthDate?.let { date -> LocalDate.parse(date) }
            it[fatherConfession] = dto.fatherConfession
            it[fatherPhone] = dto.fatherPhone
            it[motherPhone] = dto.motherPhone
            it[isShams] = dto.isShams
            it[profile] = dto.profile
            it[isReviewed] = dto.isReviewed
            it[isVerified] = dto.isVerified
            it[role] = dto.role
            it[memberId] = dto.memberId
            it[familyId] = dto.familyId
            it[classId] = dto.classId
            it[score] = dto.score
        }.let { getById(it[UsersTable.id]) }
    }

    override fun update(id: Int, dto: UserUpdateDto) = transaction {
        UsersTable.update({ UsersTable.id eq id }) { updateStatement ->
            var fullName : String? = null
            dto.firstName?.let {
                updateStatement[UsersTable.firstName] = it
                fullName = it
            }
            dto.lastName?.let {
                updateStatement[UsersTable.lastName] = it
                fullName = "$fullName $it"
            }
            fullName?.let { updateStatement[UsersTable.fullName] = it }
            dto.password?.let { updateStatement[UsersTable.password] = it }
            dto.username?.let { updateStatement[UsersTable.username] = it }
            dto.email?.let { updateStatement[UsersTable.email] = it }
            dto.phone?.let { updateStatement[UsersTable.phone] = it }
            dto.address?.let { updateStatement[UsersTable.address] = it }
            dto.birthDate?.let { updateStatement[UsersTable.birthDate] = LocalDate.parse(it) }
            dto.fatherConfession?.let { updateStatement[UsersTable.fatherConfession] = it }
            dto.fatherPhone?.let { updateStatement[UsersTable.fatherPhone] = it }
            dto.motherPhone?.let { updateStatement[UsersTable.motherPhone] = it }
            dto.isShams?.let { updateStatement[UsersTable.isShams] = it }
            dto.profile?.let { updateStatement[UsersTable.profile] = it }
            dto.isReviewed?.let { updateStatement[UsersTable.isReviewed] = it }
            dto.isVerified?.let { updateStatement[UsersTable.isVerified] = it }
            dto.role?.let { updateStatement[UsersTable.role] = it }
            dto.memberId?.let { updateStatement[UsersTable.memberId] = it }
            dto.familyId?.let { updateStatement[UsersTable.familyId] = it }
            dto.classId?.let { updateStatement[UsersTable.classId] = it }
            dto.score?.let { updateStatement[UsersTable.score] = it }
        }.let { getById(id) }
    }

    override fun delete(id: Int) = transaction {
        UsersTable.deleteWhere { UsersTable.id eq id } > 0
    }
}
