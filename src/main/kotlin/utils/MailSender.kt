package com.fathersprophets.backend.utils

import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
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
        val message = MimeMessage(session).apply {
            setFrom(InternetAddress(username, FROM_NAME))
            setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail))
            subject = "Your password reset code"
            setText("Your OTP code is $otp.\nIt expires in 10 minutes.")
        }
        Transport.send(message)
    }
}