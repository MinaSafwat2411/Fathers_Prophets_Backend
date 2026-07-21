package com.fathersprophets.backend.utils

import java.io.File

object DotEnv {
    private val values: Map<String, String> by lazy {
        val file = File(".env")
        if (!file.exists()) return@lazy emptyMap()

        file.readLines()
            .map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") && it.contains("=") }
            .associate { line ->
                val (key, value) = line.split("=", limit = 2)
                key.trim() to value.trim().removeSurrounding("\"")
            }
    }

    fun get(key: String): String? = System.getenv(key) ?: values[key]
}