package com.fathersprophets.backend.utils

import com.fathersprophets.backend.exceptions.BadRequestException
import io.ktor.http.content.PartData
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveMultipart
import io.ktor.utils.io.toByteArray

data class MultipartForm(
    val fields: Map<String, String>,
    val imageUrl: String?
)

suspend fun ApplicationCall.receiveMultipartForm(lang: String): MultipartForm {
    val fields = mutableMapOf<String, String>()
    var imageUrl: String? = null

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
                imageUrl = FileStorage.saveImage(bytes)
            }
            else -> {}
        }
        part.dispose()
        part = multipart.readPart()
    }

    return MultipartForm(fields, imageUrl)
}