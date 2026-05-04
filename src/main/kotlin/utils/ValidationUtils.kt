package com.fathersprophets.backend.utils

import com.fathersprophets.backend.exceptions.BadRequestException

object ValidationUtils {
    fun validateRequired(vararg fields: Pair<Any?, String>, lang: String) {
        fields.forEach { (value, name) ->
            if (value == null || (value is String && value.isBlank()) || (value is Number && value.toLong() <= 0L)) {
                throw BadRequestException(Localization.get("${name}_required", lang))
            }
        }
    }
}
