package com.fathersprophets.backend.services.notification

import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.repository.users.IUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class BirthdayReminderScheduler(
    private val userRepository: IUserRepository,
    private val userDao: UserDao,
    private val firebaseMessagingService: IFirebaseMessagingService
) {
    private val logger = LoggerFactory.getLogger(BirthdayReminderScheduler::class.java)

    fun start(scope: CoroutineScope, runAt: LocalTime = LocalTime.of(8, 0)) {
        scope.launch(Dispatchers.IO) {
            while (true) {
                delay(millisUntil(runAt))
                runSafely()
            }
        }
    }

    private suspend fun runSafely() {
        try {
            sendTodaysBirthdayReminders()
        } catch (e: Exception) {
            logger.error("Birthday reminder job failed", e)
        }
    }

    private suspend fun sendTodaysBirthdayReminders() {
        val todaysBirthdays = userRepository.getUpcomingBirthdays("en").data.orEmpty()
            .filter { it.daysUntil == 0L }

        if (todaysBirthdays.isEmpty()) return

        val tokens = userDao.findAllFcmTokens()
        if (tokens.isEmpty()) return

        todaysBirthdays.forEach { person ->
            firebaseMessagingService.sendToTokens(
                tokens,
                "Happy Birthday!",
                "Today is ${person.name}'s birthday!"
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