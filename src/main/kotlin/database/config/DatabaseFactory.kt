package com.fathersprophets.backend.database.config

import com.fathersprophets.backend.modules.escapeegyptanswer.EscapeEgyptAnswersTable
import com.fathersprophets.backend.modules.escapeegyptquestion.EscapeEgyptQuestionsTable
import com.fathersprophets.backend.modules.escapeegypt.EscapeEgyptTable
import com.fathersprophets.backend.modules.guesspersonanswer.GuessPersonAnswersTable
import com.fathersprophets.backend.modules.guessperson.GuessPersonTable
import com.fathersprophets.backend.modules.matchingpairanswer.MatchingPairAnswersTable
import com.fathersprophets.backend.modules.matchingpair.MatchingPairTable
import com.fathersprophets.backend.modules.timelineanswer.TimelineAnswersTable
import com.fathersprophets.backend.modules.timeline.TimelineTable
import com.fathersprophets.backend.modules.sessionattendance.SessionAttendanceTable
import com.fathersprophets.backend.modules.session.SessionTable
import com.fathersprophets.backend.modules.chatmessages.AnonymousChatMessagesTable
import com.fathersprophets.backend.modules.chat.AnonymousChatsTable
import com.fathersprophets.backend.modules.classes.ClassesTable
import com.fathersprophets.backend.modules.eventmember.EventMembersTable
import com.fathersprophets.backend.modules.event.EventsTable
import com.fathersprophets.backend.modules.notification.NotificationsTable
import com.fathersprophets.backend.modules.notificationuser.NotificationsUserTable
import com.fathersprophets.backend.modules.personofday.PersonOfDayTable
import com.fathersprophets.backend.modules.personcompleteanswer.PersonsAnswersTable
import com.fathersprophets.backend.modules.personcomplete.PersonsQuestionsTable
import com.fathersprophets.backend.modules.person.PersonsTable
import com.fathersprophets.backend.modules.personmcqanswer.PersonsMcqAnswersTable
import com.fathersprophets.backend.modules.personmcq.PersonsMcqTable
import com.fathersprophets.backend.modules.personstoryanswer.PersonStoryAnswersTable
import com.fathersprophets.backend.modules.personstoryquestion.PersonStoryQuestionsTable
import com.fathersprophets.backend.modules.personstory.PersonStoryTable
import com.fathersprophets.backend.modules.quizanswers.QuizAnswersTable
import com.fathersprophets.backend.modules.quizdayquestion.QuizDayQuestionsTable
import com.fathersprophets.backend.modules.quizday.QuizDayTable
import com.fathersprophets.backend.modules.quiz.QuizTable
import com.fathersprophets.backend.modules.supereventbooking.SuperEventBookingsTable
import com.fathersprophets.backend.modules.superevent.SuperEventsTable
import com.fathersprophets.backend.modules.comments.CommentsTable
import com.fathersprophets.backend.modules.userprogress.UserProgressQuizTable
import com.fathersprophets.backend.modules.user.UsersTable
import com.fathersprophets.backend.modules.version.VersionsTable
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
                SessionAttendanceTable,
                EventsTable,
                EventMembersTable,
                NotificationsTable,
                NotificationsUserTable,
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