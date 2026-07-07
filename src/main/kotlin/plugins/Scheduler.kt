package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.services.notification.BirthdayReminderScheduler
import io.ktor.server.application.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.ktor.ext.get

fun Application.configureScheduledJobs() {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    get<BirthdayReminderScheduler>().start(scope)
}