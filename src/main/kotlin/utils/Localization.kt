package com.fathersprophets.backend.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File

object Localization {
    private val translations = mutableMapOf<String, Map<String, String>>()

    fun load() {
        val langs = listOf("en", "ar")

        langs.forEach { lang ->
            val file = File("resources/i18n/$lang.json")
            val json = Json.parseToJsonElement(file.readText()).jsonObject

            translations[lang] = json.mapValues { it.value.jsonPrimitive.content }
        }
    }

    fun get(key: String, lang: String = "en"): String {
        return translations[lang]?.get(key)
            ?: translations["en"]?.get(key)
            ?: key
    }
}