package com.fathersprophets.backend.database.config

import com.fathersprophets.backend.database.tables.AttendanceTable
import com.fathersprophets.backend.database.tables.ClassMemberTable
import com.fathersprophets.backend.database.tables.ClassesTable
import com.fathersprophets.backend.database.tables.CommentsTable
import com.fathersprophets.backend.database.tables.EventMembersTable
import com.fathersprophets.backend.database.tables.EventsTable
import com.fathersprophets.backend.database.tables.ParentsTable
import com.fathersprophets.backend.database.tables.PersonsAnswersTable
import com.fathersprophets.backend.database.tables.PersonsMcqTable
import com.fathersprophets.backend.database.tables.PersonsQuestionsTable
import com.fathersprophets.backend.database.tables.PersonsTable
import com.fathersprophets.backend.database.tables.SessionTable
import com.fathersprophets.backend.database.tables.UsersTable
import com.fathersprophets.backend.database.tables.VersionsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {
    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/fathers_prophets",
            driver = "org.postgresql.Driver",
            user = "admin",
            password = "fathersprophets"
        )


        transaction {
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN CREATE TYPE user_role AS ENUM ('member', 'admin', 'superadmin', 'football', 'teacher', 'volleyball', 'chess', 'pingPong', 'pray', 'praise', 'doctrine', 'bible', 'ritual', 'coptic', 'choir', 'mahrgan', 'odas', 'shmas', 'sports', 'spiritual', 'melodies', 'games', 'quiz', 'parent'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'event_type') THEN CREATE TYPE event_type AS ENUM ('football', 'volleyball', 'chess', 'pingPong', 'pray', 'praise', 'doctrine', 'bible', 'ritual', 'coptic', 'choir', 'mahrgan', 'odas', 'shmas', 'melodies'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'person_type') THEN CREATE TYPE person_type AS ENUM ('prophets', 'fathers', 'saints', 'apostles', 'judges'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'question_type') THEN CREATE TYPE question_type AS ENUM ('mcq', 'complete'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'answer_status') THEN CREATE TYPE answer_status AS ENUM ('TEACHER_STILL_NOT_CORRECTED', 'IS_TRUE', 'IS_FALSE'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'mcq_correct_answer') THEN CREATE TYPE mcq_correct_answer AS ENUM ('1', '2', '3', '4'); END IF; END $$;")

            SchemaUtils.create(
                UsersTable,
                ClassesTable,
                CommentsTable,
                ClassMemberTable,
                VersionsTable,
                ParentsTable,
                SessionTable,
                AttendanceTable,
                EventsTable,
                EventMembersTable,
                PersonsTable,
                PersonsQuestionsTable,
                PersonsMcqTable,
                PersonsAnswersTable
            )
        }
    }
}