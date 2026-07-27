package com.fathersprophets.backend.models.attendance

import kotlinx.serialization.Serializable

@Serializable
data class AddAttendanceBulkRequest(
    val sessionId: Int? = null,
    val classId: Int? = null,
    val records: List<AddAttendanceRequest> = emptyList()
) {
    /** A whole class shares one session, so records fall back to the top level values. */
    fun resolvedRecords() = records.map {
        it.copy(
            sessionId = it.sessionId ?: sessionId,
            classId = it.classId ?: classId
        )
    }
}