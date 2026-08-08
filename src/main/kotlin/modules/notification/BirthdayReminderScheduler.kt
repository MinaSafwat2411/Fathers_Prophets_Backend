package com.fathersprophets.backend.modules.notification

import com.fathersprophets.backend.modules.token.TokenDao
import com.fathersprophets.backend.database.tables.user.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.time.Duration.Companion.milliseconds

class BirthdayReminderScheduler(
    private val userDao: UserDao,
    private val tokenDao: TokenDao,
    private val firebaseMessagingService: IFirebaseMessagingService
) {
    private val logger = LoggerFactory.getLogger(BirthdayReminderScheduler::class.java)

    fun start(scope: CoroutineScope, runAt: LocalTime = LocalTime.of(8, 0)) {
        scope.launch(Dispatchers.IO) {
            while (true) {
                delay(millisUntil(runAt).milliseconds)
                runSafely()
            }
        }
    }

    private fun runSafely() {
        try {
            sendTodaysBirthdayReminders()
        } catch (e: Exception) {
            logger.error("Birthday reminder job failed", e)
        }
    }

    private fun sendTodaysBirthdayReminders() {
        val today = LocalDate.now()

        val todaysBirthdays = userDao.getUsersWithBirthDate().filter { user ->
            val birthDate = user.birthDate?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
            birthDate != null && birthDate.month == today.month && birthDate.dayOfMonth == today.dayOfMonth
        }

        if (todaysBirthdays.isEmpty()) return

        val tokens = tokenDao.getAllFcmTokens()
        if (tokens.isEmpty()) return

        todaysBirthdays.forEach { user ->
            firebaseMessagingService.sendToTokens(
                tokens,
                "Happy Birthday!",
                "Today is ${user.fullName}'s birthday!"
            )
        }
    }

    private fun millisUntil(time: LocalTime): Long {
        val now = LocalDateTime.now()
        var next = now.toLocalDate().atTime(time)
        if (!next.isAfter(now)) next = next.plusDays(1)
        return Duration.between(now, next).toMillis()
    }
}