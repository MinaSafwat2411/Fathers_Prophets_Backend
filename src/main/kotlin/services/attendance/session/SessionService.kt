package com.fathersprophets.backend.services.attendance.session

import com.fathersprophets.backend.database.repository.attendance.sessions.ISessionRepository
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.session.AddSessionRequest
import com.fathersprophets.backend.models.session.SessionResponse
import com.fathersprophets.backend.models.session.UpdateSessionRequest
import com.fathersprophets.backend.utils.Localization
import com.fathersprophets.backend.utils.SessionEventBroadcaster
import com.fathersprophets.backend.utils.ValidationUtils.validateRequired
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionService(
    private val sessionRepository: ISessionRepository
) : ISessionService {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun createSession(
        addSessionRequest: AddSessionRequest,
        lang: String
    ): ApiResponse<SessionResponse> {
        validateRequired(
            addSessionRequest.dateTime to "date_time",
            lang = lang
        )
        val result = sessionRepository.createSession(addSessionRequest, lang)
        if (result.success) {
            broadcastSessions()
        }
        return result
    }


    override fun deleteSession(
        sessionId: Int?,
        lang: String
    ): ApiResponse<Nothing> {
        if (sessionId == null) {
            return ApiResponse(success = false, message = Localization.get("invalid_id", lang))
        }
        val result = sessionRepository.deleteSession(sessionId, lang)
        if (result.success) {
            broadcastSessions()
        }
        return result
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
        val result = sessionRepository.updateSession(sessionId, updateSessionRequest, lang)
        if (result.success) {
            broadcastSessions()
        }
        return result
    }

    private fun broadcastSessions() {
        scope.launch {
            SessionEventBroadcaster.broadcastSessions(getAllSessions())
        }
    }
}
