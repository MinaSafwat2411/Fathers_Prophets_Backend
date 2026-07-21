package com.fathersprophets.backend.utils

import com.fathersprophets.backend.exceptions.BadRequestException
import io.ktor.http.content.PartData
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveMultipart
import io.ktor.utils.io.toByteArray
import java.util.Base64

data class MultipartForm(
    val fields: Map<String, String>,
    val base64Image: String?
)

suspend fun ApplicationCall.receiveMultipartForm(lang: String): MultipartForm {
    val fields = mutableMapOf<String, String>()
    var base64Image: String? = null

    val multipart = receiveMultipart()
    var part = multipart.readPart()
    while (part != null) {
        when (part) {
            is PartData.FormItem -> part.name?.let { fields[it] = part.value }
            is PartData.FileItem -> {
                val bytes = part.provider().toByteArray()
                if (!ImageUtils.isImage(bytes)) {
                    throw BadRequestException(Localization.get("invalid_image_format", lang))
                }
                base64Image = Base64.getEncoder().encodeToString(bytes)
            }
            else -> {}
        }
        part.dispose()
        part = multipart.readPart()
    }

    return MultipartForm(fields, base64Image)
}