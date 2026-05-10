package com.fathersprophets.backend.services.session

import com.fathersprophets.backend.database.repository.sessions.ISessionRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.session.AddSessionRequest
import com.fathersprophets.backend.models.session.SessionResponse
import com.fathersprophets.backend.models.session.UpdateSessionRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired

class SessionService(
    private val sessionRepository: ISessionRepository
) : ISessionService {
    override fun createSession(
        addSessionRequest: AddSessionRequest,
        lang: String
    ): ApiResponse<SessionResponse> {
        validateRequired(
            addSessionRequest.dateTime to "date_time",
            lang = lang
        )
        return sessionRepository.createSession(addSessionRequest, lang)
    }

    override fun getSessionById(
        sessionId: Int?,
        lang: String
    ): ApiResponse<SessionResponse> {
        if (sessionId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }
        return sessionRepository.getSessionById(sessionId, lang)
    }

    override fun deleteSession(
        sessionId: Int?,
        lang: String
    ): ApiResponse<Nothing> {
        if (sessionId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }
        return sessionRepository.deleteSession(sessionId, lang)
    }

    override fun getAllSessions(): ApiResponse<List<SessionResponse>> {
        return sessionRepository.getAllSessions()
    }

    override fun updateSession(
        sessionId: Int?,
        updateSessionRequest: UpdateSessionRequest,
        lang: String
    ): ApiResponse<SessionResponse> {
        if (sessionId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }
        return sessionRepository.updateSession(sessionId, updateSessionRequest, lang)
    }
}
