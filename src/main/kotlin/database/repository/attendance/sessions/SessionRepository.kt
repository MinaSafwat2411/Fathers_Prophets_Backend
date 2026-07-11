package com.fathersprophets.backend.database.repository.attendance.sessions

import com.fathersprophets.backend.database.dao.attendance.SessionDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.SessionDto
import com.fathersprophets.backend.models.session.AddSessionRequest
import com.fathersprophets.backend.models.session.SessionResponse
import com.fathersprophets.backend.models.session.UpdateSessionRequest

class SessionRepository(
    private val sessionDao: SessionDao
) : ISessionRepository {
    override fun createSession(
        addSessionRequest: AddSessionRequest,
        lang: String
    ): ApiResponse<Int> {
        val id = sessionDao.addSession(addSessionRequest.toSessionDto())

        if (id == 0) throw IllegalStateException("session_not_added")


        return ApiResponse(
            success = true,
            data = id,
            message = "session_added_successfully"
        )

    }

    override fun getSessionById(
        sessionId: Int,
        lang: String
    ): ApiResponse<SessionResponse> {
        val id = sessionDao.getSessionById(sessionId)
            ?: throw IllegalStateException("session_not_found")


        return ApiResponse(
            success = true,
            data = id.convertToSessionResponse(),
            message = "session_retrieved_successfully"
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
    ): ApiResponse<Nothing> {

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