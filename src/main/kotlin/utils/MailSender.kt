package com.fathersprophets.backend.utils

import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import java.util.Properties

object MailSender {
    private const val FROM_NAME = "Fathers Prophets"

    private val host = DotEnv.get("SMTP_HOST") ?: "smtp.gmail.com"
    private val port = DotEnv.get("SMTP_PORT") ?: "587"
    private val username = DotEnv.get("SMTP_USERNAME") ?: ""
    private val password = DotEnv.get("SMTP_PASSWORD") ?: ""

    private val session: Session by lazy {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", host)
            put("mail.smtp.port", port)
        }
        Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication() = PasswordAuthentication(username, password)
        })
    }

    fun sendOtpEmail(toEmail: String, otp: String) {
        val textPart = MimeBodyPart().apply {
            setText(
                "Your $FROM_NAME verification code is $otp\n" +
                    "It expires in 10 minutes.\n\n" +
                    "If you didn't request this, you can safely ignore this email."
            )
        }

        val htmlPart = MimeBodyPart().apply {
            setContent(buildHtmlBody(otp), "text/html; charset=utf-8")
        }

        val multipart = MimeMultipart("alternative").apply {
            addBodyPart(textPart)
            addBodyPart(htmlPart)
        }

        val message = MimeMessage(session).apply {
            setFrom(InternetAddress(username, FROM_NAME))
            setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail))
            // Leading with the code means most mail apps surface it directly in the
            // notification preview, so it can be copied without opening the email.
            subject = "$otp is your $FROM_NAME verification code"
            setContent(multipart)
        }

        Transport.send(message)
    }

    private fun buildHtmlBody(otp: String) = """
        <div style="font-family: Arial, Helvetica, sans-serif; max-width: 480px; margin: 0 auto; padding: 24px; color: #1a1a1a;">
            <h2 style="margin: 0 0 16px;">$FROM_NAME</h2>
            <p style="margin: 0 0 8px;">Use the code below to reset your password. It expires in 10 minutes.</p>
            <div style="margin: 24px 0; padding: 16px; background: #f4f4f5; border-radius: 8px; text-align: center;">
                <span style="font-size: 32px; font-weight: bold; letter-spacing: 8px; font-family: 'Courier New', monospace;">$otp</span>
            </div>
            <p style="color: #666; font-size: 13px; margin: 0;">If you didn't request this code, you can safely ignore this email.</p>
        </div>
    """.trimIndent()
}