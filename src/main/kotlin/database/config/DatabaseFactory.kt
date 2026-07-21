package com.fathersprophets.backend.database.config

import com.fathersprophets.backend.database.tables.activity.escapeegypt.EscapeEgyptAnswersTable
import com.fathersprophets.backend.database.tables.activity.escapeegypt.EscapeEgyptQuestionsTable
import com.fathersprophets.backend.database.tables.activity.escapeegypt.EscapeEgyptTable
import com.fathersprophets.backend.database.tables.activity.guessperson.GuessPersonAnswersTable
import com.fathersprophets.backend.database.tables.person.guessperson.GuessPersonTable
import com.fathersprophets.backend.database.tables.activity.matchingair.MatchingPairAnswersTable
import com.fathersprophets.backend.database.tables.activity.matchingair.MatchingPairTable
import com.fathersprophets.backend.database.tables.activity.timeline.TimelineAnswersTable
import com.fathersprophets.backend.database.tables.activity.timeline.TimelineTable
import com.fathersprophets.backend.database.tables.attendance.AttendanceTable
import com.fathersprophets.backend.database.tables.attendance.SessionTable
import com.fathersprophets.backend.database.tables.chat.AnonymousChatMessagesTable
import com.fathersprophets.backend.database.tables.chat.AnonymousChatsTable
import com.fathersprophets.backend.database.tables.classes.ClassMemberTable
import com.fathersprophets.backend.database.tables.classes.ClassesTable
import com.fathersprophets.backend.database.tables.event.EventMembersTable
import com.fathersprophets.backend.database.tables.event.EventsTable
import com.fathersprophets.backend.database.tables.notification.NotificationsTable
import com.fathersprophets.backend.database.tables.person.personofday.PersonOfDayTable
import com.fathersprophets.backend.database.tables.person.complete.PersonsAnswersTable
import com.fathersprophets.backend.database.tables.person.complete.PersonsQuestionsTable
import com.fathersprophets.backend.database.tables.person.PersonsTable
import com.fathersprophets.backend.database.tables.person.mcq.PersonsMcqAnswersTable
import com.fathersprophets.backend.database.tables.person.mcq.PersonsMcqTable
import com.fathersprophets.backend.database.tables.person.story.PersonStoryAnswersTable
import com.fathersprophets.backend.database.tables.person.story.PersonStoryQuestionsTable
import com.fathersprophets.backend.database.tables.person.story.PersonStoryTable
import com.fathersprophets.backend.database.tables.quiz.QuizAnswersTable
import com.fathersprophets.backend.database.tables.quiz.QuizDayQuestionsTable
import com.fathersprophets.backend.database.tables.quiz.QuizDayTable
import com.fathersprophets.backend.database.tables.quiz.QuizTable
import com.fathersprophets.backend.database.tables.superevent.SuperEventBookingsTable
import com.fathersprophets.backend.database.tables.superevent.SuperEventsTable
import com.fathersprophets.backend.database.tables.users.CommentsTable
import com.fathersprophets.backend.database.tables.users.ParentsTable
import com.fathersprophets.backend.database.tables.users.UserProgressQuizTable
import com.fathersprophets.backend.database.tables.users.UsersTable
import com.fathersprophets.backend.database.tables.vesion.VersionsTable
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
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'anonymous_chat_status') THEN CREATE TYPE anonymous_chat_status AS ENUM ('OPEN', 'CLOSED'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'escape_egypt_type') THEN CREATE TYPE escape_egypt_type AS ENUM ('from', 'to'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'day_of_week') THEN CREATE TYPE day_of_week AS ENUM ('SAT', 'SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'quiz_day_type') THEN CREATE TYPE quiz_day_type AS ENUM ('TRUE_FALSE', 'MCQ'); END IF; END $$;")
            exec("DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'super_event_booking_status') THEN CREATE TYPE super_event_booking_status AS ENUM ('booked', 'waiting', 'cancelled'); END IF; END $$;")

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
                NotificationsTable,
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

            exec("ALTER TABLE users ALTER COLUMN profile TYPE TEXT")
            exec("ALTER TABLE users ADD COLUMN IF NOT EXISTS otp_code VARCHAR(10)")
            exec("ALTER TABLE users ADD COLUMN IF NOT EXISTS otp_expires_at TIMESTAMP")
            exec("ALTER TABLE users ADD COLUMN IF NOT EXISTS reset_transaction_id VARCHAR(64)")
            exec("ALTER TABLE users ADD COLUMN IF NOT EXISTS reset_verify_token VARCHAR(64)")
            exec("ALTER TABLE users ADD COLUMN IF NOT EXISTS reset_verify_token_expires_at TIMESTAMP")
            exec("CREATE UNIQUE INDEX IF NOT EXISTS users_reset_transaction_id_unique ON users (reset_transaction_id)")
            exec("CREATE UNIQUE INDEX IF NOT EXISTS users_reset_verify_token_unique ON users (reset_verify_token)")
        }
    }
}