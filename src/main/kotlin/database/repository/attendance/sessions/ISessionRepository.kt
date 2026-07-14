package com.fathersprophets.backend.database.repository.attendance.sessions

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.session.AddSessionRequest
import com.fathersprophets.backend.models.session.SessionResponse
import com.fathersprophets.backend.models.session.UpdateSessionRequest

interface ISessionRepository {
    fun createSession(addSessionRequest: AddSessionRequest,lang: String): ApiResponse<SessionResponse>
    fun deleteSession(sessionId: Int,lang: String): ApiResponse<Nothing>
    fun getAllSessions(): ApiResponse<List<SessionResponse>>
    fun updateSession(sessionId: Int,updateSessionRequest: UpdateSessionRequest,lang: String): ApiResponse<SessionResponse>
}