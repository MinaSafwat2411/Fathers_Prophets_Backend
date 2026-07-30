package com.fathersprophets.backend.database.repository.attendance.sessions

import com.fathersprophets.backend.database.dao.SessionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.session.AddSessionRequest
import com.fathersprophets.backend.models.session.SessionResponse
import com.fathersprophets.backend.models.session.UpdateSessionRequest

class SessionRepository(
    private val sessionDao: SessionDao
) : ISessionRepository {
    override fun createSession(
        addSessionRequest: AddSessionRequest,
        lang: String
    ): ApiResponse<SessionResponse> {
        val created = sessionDao.addSession(addSessionRequest.toSessionDto())
            ?: throw IllegalStateException("session_not_added")


        return ApiResponse(
            success = true,
            data = created.convertToSessionResponse(),
            message = "session_added_successfully"
        )

    }

    override fun deleteSession(
        sessionId: Int,
        lang: String
    ): ApiResponse<Nothing> {
        val deleted = sessionDao.deleteSession(sessionId)

        if (!deleted) throw IllegalStateException("session_not_found")

        return ApiResponse(
            success = true,
            data = null,
            message = "session_deleted_successfully"
        )
    }

    override fun getAllSessions(): ApiResponse<List<SessionResponse>> {
        val sessions = sessionDao.getAllSessions()
        return ApiResponse(
            success = true,
            data = sessions.map { it.convertToSessionResponse() },
            message = "sessions_retrieved_successfully"
        )
    }

    override fun updateSession(
        sessionId: Int,
        updateSessionRequest: UpdateSessionRequest,
        lang: String
    ): ApiResponse<SessionResponse> {

        val sessionDto = updateSessionRequest.toSessionDto(sessionId)

        val updatedSession = sessionDao.updateSession(sessionDto)

        if (!updatedSession) throw IllegalStateException("session_not_found")

        return ApiResponse(
            success = true,
            data = null,
            message = "session_updated_successfully"
        )
    }
}