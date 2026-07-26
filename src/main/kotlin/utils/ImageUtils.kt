package com.fathersprophets.backend.utils

object ImageUtils {
    private val JPEG = byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte())
    private val PNG = byteArrayOf(0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A)
    private val GIF = byteArrayOf(0x47, 0x49, 0x46, 0x38)
    private val BMP = byteArrayOf(0x42, 0x4D)
    private val RIFF = byteArrayOf(0x52, 0x49, 0x46, 0x46)
    private val WEBP = byteArrayOf(0x57, 0x45, 0x42, 0x50)

    fun isImage(bytes: ByteArray): Boolean {
        return bytes.startsWith(JPEG) ||
            bytes.startsWith(PNG) ||
            bytes.startsWith(GIF) ||
            bytes.startsWith(BMP) ||
            (bytes.startsWith(RIFF) && bytes.size >= 12 && bytes.copyOfRange(8, 12).contentEquals(WEBP))
    }

    fun extensionFor(bytes: ByteArray): String = when {
        bytes.startsWith(PNG) -> "png"
        bytes.startsWith(JPEG) -> "jpg"
        bytes.startsWith(GIF) -> "gif"
        bytes.startsWith(BMP) -> "bmp"
        bytes.startsWith(RIFF) && bytes.size >= 12 && bytes.copyOfRange(8, 12).contentEquals(WEBP) -> "webp"
        else -> "bin"
    }

    private fun ByteArray.startsWith(signature: ByteArray): Boolean {
        if (size < signature.size) return false
        return signature.indices.all { this[it] == signature[it] }
    }
}