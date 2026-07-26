package com.fathersprophets.backend.utils

import java.io.File
import java.util.UUID

object FileStorage {
    const val URL_PATH = "/uploads"

    val uploadDir: File = File("uploads").apply { mkdirs() }

    private val publicBaseUrl = DotEnv.get("PUBLIC_BASE_URL")?.trimEnd('/') ?: ""

    fun saveImage(bytes: ByteArray): String {
        val filename = "${UUID.randomUUID()}.${ImageUtils.extensionFor(bytes)}"
        File(uploadDir, filename).writeBytes(bytes)
        return "$publicBaseUrl$URL_PATH/$filename"
    }
}
