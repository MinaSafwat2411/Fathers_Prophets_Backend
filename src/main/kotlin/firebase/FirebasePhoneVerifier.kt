package com.fathersprophets.backend.firebase

import com.fathersprophets.backend.exceptions.UnauthorizedException
import com.fathersprophets.backend.utils.Localization
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

object FirebasePhoneVerifier {
    fun verifyPhoneToken(idToken: String, lang: String): String {
        val decoded = try {
            FirebaseAuth.getInstance().verifyIdToken(idToken)
        } catch (e: FirebaseAuthException) {
            throw UnauthorizedException(Localization.get("invalid_phone_token", lang))
        }

        return decoded.claims["phone_number"] as? String
            ?: throw UnauthorizedException(Localization.get("invalid_phone_token", lang))
    }
}
