package com.fathersprophets.backend.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object WhatsAppSender {
    private val phoneNumberId = DotEnv.get("WHATSAPP_PHONE_NUMBER_ID") ?: ""
    private val accessToken = DotEnv.get("WHATSAPP_ACCESS_TOKEN") ?: ""
    private val templateName = DotEnv.get("WHATSAPP_TEMPLATE_NAME") ?: "otp_verification"
    private val templateLang = DotEnv.get("WHATSAPP_TEMPLATE_LANG") ?: "en_US"
    private val apiVersion = DotEnv.get("WHATSAPP_API_VERSION") ?: "v21.0"

    private val client = OkHttpClient()

    fun sendOtpWhatsApp(toPhone: String, otp: String) {
        // Meta's Cloud API expects the number without a leading '+' or separators.
        val normalizedPhone = toPhone.filter { it.isDigit() }

        val payload = WhatsAppMessage(
            to = normalizedPhone,
            template = WhatsAppTemplate(
                name = templateName,
                language = WhatsAppLanguage(code = templateLang),
                components = listOf(
                    WhatsAppComponent(type = "body", parameters = listOf(WhatsAppParameter(text = otp))),
                    WhatsAppComponent(
                        type = "button",
                        subType = "url",
                        index = "0",
                        parameters = listOf(WhatsAppParameter(text = otp))
                    )
                )
            )
        )

        val requestBody = Json.encodeToString(payload).toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://graph.facebook.com/$apiVersion/$phoneNumberId/messages")
            .addHeader("Authorization", "Bearer $accessToken")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw RuntimeException("Failed to send WhatsApp OTP: ${response.code} ${response.body?.string()}")
            }
        }
    }
}

@Serializable
private data class WhatsAppMessage(
    @SerialName("messaging_product") val messagingProduct: String = "whatsapp",
    val to: String,
    val type: String = "template",
    val template: WhatsAppTemplate
)

@Serializable
private data class WhatsAppTemplate(
    val name: String,
    val language: WhatsAppLanguage,
    val components: List<WhatsAppComponent>
)

@Serializable
private data class WhatsAppLanguage(val code: String)

@Serializable
private data class WhatsAppComponent(
    val type: String,
    @SerialName("sub_type") val subType: String? = null,
    val index: String? = null,
    val parameters: List<WhatsAppParameter>
)

@Serializable
private data class WhatsAppParameter(val type: String = "text", val text: String)
