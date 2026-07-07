package com.fathersprophets.backend.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

object FirebaseFactory {
    fun init() {
        if (FirebaseApp.getApps().isNotEmpty()) return

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(resolveCredentialsStream()))
            .build()

        FirebaseApp.initializeApp(options)
    }

    private fun resolveCredentialsStream(): InputStream {
        val path = System.getenv("FIREBASE_CREDENTIALS_PATH")
        if (!path.isNullOrBlank()) {
            val file = File(path)
            if (file.exists()) return FileInputStream(file)
        }

        return FirebaseFactory::class.java.getResourceAsStream("/firebase-service-account.json")
            ?: throw IllegalStateException(
                "Firebase credentials not found. Set the FIREBASE_CREDENTIALS_PATH environment " +
                    "variable to the service-account JSON path, or place the file at " +
                    "src/main/resources/firebase-service-account.json"
            )
    }
}