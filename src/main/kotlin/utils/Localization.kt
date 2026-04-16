package com.fathersprophets.backend.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object Localization {

    private val translations = mutableMapOf<String, Map<String, String>>()

    fun load() {
        val langList = listOf("en", "ar")

        langList.forEach { lang ->

            val stream = this::class.java
                .classLoader
                .getResourceAsStream("i18n/$lang.json")
                ?: throw Exception("Missing file: i18n/$lang.json")

            val text = stream.bufferedReader().use { it.readText() }

            val json = Json.parseToJsonElement(text).jsonObject

            translations[lang] = json.mapValues { it.value.jsonPrimitive.content }
        }
    }

    fun get(key: String, lang: String = "en"): String {
        return translations[lang]?.get(key)
            ?: translations["en"]?.get(key)
            ?: key
    }
}