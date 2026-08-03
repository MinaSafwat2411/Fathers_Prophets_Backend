package com.fathersprophets.backend.database.config

import com.fathersprophets.backend.database.tables.escapeegypt.EscapeEgyptAnswersTable
import com.fathersprophets.backend.database.tables.escapeegypt.EscapeEgyptQuestionsTable
import com.fathersprophets.backend.database.tables.escapeegypt.EscapeEgyptTable
import com.fathersprophets.backend.database.tables.guessperson.GuessPersonAnswersTable
import com.fathersprophets.backend.database.tables.guessperson.GuessPersonTable
import com.fathersprophets.backend.database.tables.matchingpair.MatchingPairAnswersTable
import com.fathersprophets.backend.database.tables.matchingpair.MatchingPairTable
import com.fathersprophets.backend.database.tables.TimelineAnswersTable
import com.fathersprophets.backend.database.tables.TimelineTable
import com.fathersprophets.backend.database.tables.session.AttendanceTable
import com.fathersprophets.backend.database.tables.session.SessionTable
import com.fathersprophets.backend.database.tables.chat.AnonymousChatMessagesTable
import com.fathersprophets.backend.database.tables.chat.AnonymousChatsTable
import com.fathersprophets.backend.database.tables.classes.ClassesTable
import com.fathersprophets.backend.database.tables.event.EventMembersTable
import com.fathersprophets.backend.database.tables.event.EventsTable
import com.fathersprophets.backend.database.tables.NotificationsTable
import com.fathersprophets.backend.database.tables.UserNotificationsTable
import com.fathersprophets.backend.database.tables.PersonOfDayTable
import com.fathersprophets.backend.database.tables.PersonsAnswersTable
import com.fathersprophets.backend.database.tables.PersonsQuestionsTable
import com.fathersprophets.backend.database.tables.PersonsTable
import com.fathersprophets.backend.database.tables.PersonsMcqAnswersTable
import com.fathersprophets.backend.database.tables.PersonsMcqTable
import com.fathersprophets.backend.database.tables.PersonStoryAnswersTable
import com.fathersprophets.backend.database.tables.PersonStoryQuestionsTable
import com.fathersprophets.backend.database.tables.PersonStoryTable
import com.fathersprophets.backend.database.tables.QuizAnswersTable
import com.fathersprophets.backend.database.tables.QuizDayQuestionsTable
import com.fathersprophets.backend.database.tables.QuizDayTable
import com.fathersprophets.backend.database.tables.QuizTable
import com.fathersprophets.backend.database.tables.SuperEventBookingsTable
import com.fathersprophets.backend.database.tables.SuperEventsTable
import com.fathersprophets.backend.database.tables.comments.CommentsTable
import com.fathersprophets.backend.database.tables.UserProgressQuizTable
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
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'question_type') THEN CREATE TYPE question_type AS ENUM ('mcq', 'complete'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'answer_status') THEN CREATE TYPE answer_status AS ENUM ('TEACHER_STILL_NOT_CORRECTED', 'IS_TRUE', 'IS_FALSE'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'mcq_correct_answer') THEN CREATE TYPE mcq_correct_answer AS ENUM ('first', 'second', 'third', 'fourth'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'anonymous_chat_status') THEN CREATE TYPE anonymous_chat_status AS ENUM ('OPEN', 'CLOSED'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'escape_egypt_type') THEN CREATE TYPE escape_egypt_type AS ENUM ('from', 'to'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'day_of_week') THEN CREATE TYPE day_of_week AS ENUM ('SAT', 'SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'quiz_day_type') THEN CREATE TYPE quiz_day_type AS ENUM ('TRUE_FALSE', 'MCQ'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'super_event_booking_status') THEN CREATE TYPE super_event_booking_status AS ENUM ('booked', 'waiting', 'cancelled'); END IF; END $$;")

            SchemaUtils.create(
                UsersTable,
                ClassesTable,
                CommentsTable,
                VersionsTable,
                SessionTable,
                AttendanceTable,
                EventsTable,
                EventMembersTable,
                NotificationsTable,
                UserNotificationsTable,
                PersonsTable,
                PersonsQuestionsTable,
                PersonsMcqTable,
                PersonsAnswersTable,
                PersonsMcqAnswersTable,
                PersonOfDayTable,
                PersonStoryAnswersTable,
                PersonStoryTable,
                PersonStoryQuestionsTable,
                GuessPersonTable,
                GuessPersonAnswersTable,
                MatchingPairTable,
                MatchingPairAnswersTable,
                EscapeEgyptAnswersTable,
                EscapeEgyptQuestionsTable,
                EscapeEgyptTable,
                TimelineAnswersTable,
                TimelineTable,
                QuizAnswersTable,
                QuizDayQuestionsTable,
                QuizDayTable,
                QuizTable,
                UserProgressQuizTable,
                AnonymousChatMessagesTable,
                AnonymousChatsTable,
                SuperEventsTable,
                SuperEventBookingsTable,
            )
        }
    }
}