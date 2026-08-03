package com.fathersprophets.backend.database.tables

import com.fathersprophets.backend.database.enums.UserRole
import com.fathersprophets.backend.database.tables.classes.ClassesTable
import com.fathersprophets.backend.database.tables.family.FamilyTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PGobject

object UsersTable : Table("users") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val password = varchar("password", 255)
    val username = varchar("username", 255).uniqueIndex()
    val email = varchar("email", 255).nullable().uniqueIndex()
    val phone = varchar("phone", 50).nullable().uniqueIndex()
    val address = varchar("address", 255).nullable()
    val birthDate = date("birth_date").nullable()
    val fatherConfession = varchar("father_confession", 255).nullable()
    val fatherPhone = varchar("father_phone", 255).nullable()
    val motherPhone = varchar("mother_phone", 255).nullable()
    val isShams = bool("is_shams").default(false)
    val profile = text("profile").nullable()
    val isReviewed = bool("is_reviewed").default(false)
    val role = customEnumeration(
        "role",
        "user_role",
        { value -> UserRole.entries.first { it.name.equals((value as String), ignoreCase = true) } },
        { PGobject().apply { type = "user_role"; value = it.name.lowercase() } }
    ).index("idx_users_role")
    val memberId = varchar("member_id", 100).nullable().uniqueIndex()
    val familyId = integer("family_id").references(FamilyTable.id , onDelete = ReferenceOption.CASCADE).nullable().index("idx_users_family_id")
    val classId = integer("class_id").references(ClassesTable.id, onDelete = ReferenceOption.CASCADE).nullable().index("idx_users_class_id")
    val score = integer("score").default(0)

    override val primaryKey = PrimaryKey(id)

    init {
        // NOTE: Safely creates the PostgreSQL ENUM type on startup if it doesn't already exist
        TransactionManager.current().exec(
            """
            DO $$ BEGIN 
                IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN 
                    CREATE TYPE user_role AS ENUM (
                        'member', 'admin', 'superadmin', 'football', 'teacher', 
                        'volleyball', 'chess', 'pingpong', 'pray', 'praise', 
                        'doctrine', 'bible', 'ritual', 'coptic', 'choir', 
                        'carnival', 'odas', 'deacon', 'sports', 'spiritual', 
                        'melodies', 'games', 'quiz', 'parent'
                    ); 
                END IF; 
            END $$;
            """.trimIndent()
        )

        // NOTE: Validates that the phone number starts with approved prefixes (010, 011, 012, 015) or is null
        check("valid_phone_prefix") {
            phone.isNull() or
                    phone.like("010%") or
                    phone.like("011%") or
                    phone.like("012%") or
                    phone.like("015%")
        }

        // NOTE: Validates father's phone prefix constraints
        check("valid_father_phone_prefix") {
            fatherPhone.isNull() or
                    fatherPhone.like("010%") or
                    fatherPhone.like("011%") or
                    fatherPhone.like("012%") or
                    fatherPhone.like("015%")
        }

        // NOTE: Validates mother's phone prefix constraints
        check("valid_mother_phone_prefix") {
            motherPhone.isNull() or
                    motherPhone.like("010%") or
                    motherPhone.like("011%") or
                    motherPhone.like("012%") or
                    motherPhone.like("015%")
        }

        // NOTE: Enforces strict structural format for member ID (e.g., E1C1 + number up to 10000 + R + digit 0-9)
        check("valid_member_id_format") {
            memberId.isNull() or
                    memberId.regexp("^E1C1([0-9]{1,4}|10000)R[0-9]$")
        }

        // NOTE: Validates proper standard email formatting if an email address is provided
        check("valid_email_format") {
            email.isNull() or (email.regexp("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
        }
    }
}