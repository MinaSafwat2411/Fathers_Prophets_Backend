package com.fathersprophets.backend.database.repository.sessions

import com.fathersprophets.backend.database.dao.SessionDao
import com.fathersprophets.backend.database.tables.SessionTable.createdAt
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
    ): ApiResponse<SessionResponse> {
        val id = sessionDao.addSession(addSessionRequest.toSessionDto())

        val session = sessionDao.getSessionById(id) ?: throw IllegalStateException("session_create_failed")

        return ApiResponse(
            success = true,
            data = session.convertToSessionResponse(),
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
        sessionDao.deleteSession(idToSession(sessionId))
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

        return ApiResponse(
            success = true,
            data = updatedSession.convertToSessionResponse(),
            message = "session_updated_successfully"
        )
    }

    private fun idToSession(sessionId: Int) = SessionDto(
        id = sessionId,
        createdAt = "",
        dateTime = ""
    )
}