package com.fathersprophets.backend.database.config

import com.fathersprophets.backend.database.tables.classes.ClassesTable
import com.fathersprophets.backend.database.tables.family.FamilyTable
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
            SchemaUtils.create(
                ClassesTable,
                FamilyTable,
//                UsersTable,
//                CommentsTable,
//                VersionsTable,
//                SessionTable,
//                SessionAttendanceTable,
//                EventsTable,
//                EventMembersTable,
//                NotificationsTable,
//                NotificationsUserTable,
//                PersonsTable,
//                PersonsQuestionsTable,
//                PersonsMcqTable,
//                PersonsAnswersTable,
//                PersonsMcqAnswersTable,
//                PersonOfDayTable,
//                PersonStoryAnswersTable,
//                PersonStoryTable,
//                PersonStoryQuestionsTable,
//                GuessPersonTable,
//                GuessPersonAnswersTable,
//                MatchingPairTable,
//                MatchingPairAnswersTable,
//                EscapeEgyptAnswersTable,
//                EscapeEgyptQuestionsTable,
//                EscapeEgyptTable,
//                TimelineAnswersTable,
//                TimelineTable,
//                QuizAnswersTable,
//                QuizDayQuestionsTable,
//                QuizDayTable,
//                QuizTable,
//                UserProgressQuizTable,
//                AnonymousChatMessagesTable,
//                AnonymousChatsTable,
//                SuperEventsTable,
//                SuperEventBookingsTable,
            )
        }
    }
}