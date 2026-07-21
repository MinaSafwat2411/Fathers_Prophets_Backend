package com.fathersprophets.backend.plugins

import com.fathersprophets.backend.exceptions.TooManyRequestsException
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.plugins.origin
import io.ktor.server.request.path
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

object RateLimiter {
    private const val MAX_ATTEMPTS = 5
    private const val INITIAL_BLOCK_SECONDS = 10L
    private const val MAX_BLOCK_SECONDS = 3600L

    private class State {
        var attempts: Int = 0
        var blockedUntil: Instant? = null
        var nextBlockSeconds: Long = INITIAL_BLOCK_SECONDS
    }

    private val states = ConcurrentHashMap<String, State>()

    /** Returns how long the caller must still wait, or null if the request is allowed. */
    fun check(key: String): Duration? {
        val state = states.getOrPut(key) { State() }

        synchronized(state) {
            val now = Instant.now()
            val blockedUntil = state.blockedUntil

            if (blockedUntil != null && now.isBefore(blockedUntil)) {
                return Duration.between(now, blockedUntil)
            }

            state.attempts++
            if (state.attempts > MAX_ATTEMPTS) {
                val waitSeconds = state.nextBlockSeconds
                state.blockedUntil = now.plusSeconds(waitSeconds)
                state.nextBlockSeconds = (waitSeconds * 2).coerceAtMost(MAX_BLOCK_SECONDS)
                state.attempts = 0
                return Duration.ofSeconds(waitSeconds)
            }

            return null
        }
    }
}

val RateLimitPlugin = createRouteScopedPlugin("RateLimitPlugin") {
    onCall { call ->
        val key = "${call.request.origin.remoteHost}:${call.request.path()}"
        val blockedFor = RateLimiter.check(key)

        if (blockedFor != null) {
            val seconds = blockedFor.seconds + 1
            throw TooManyRequestsException("Too many attempts. Try again in $seconds seconds.")
        }
    }
}