package com.fathersprophets.backend.services.version

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.version.AdminPinRequest
import com.fathersprophets.backend.models.version.VersionRequest
import com.fathersprophets.backend.database.dto.version.VersionDto

interface IVersionService {

    suspend fun getLastVersion(lang : String) : ApiResponse<VersionDto>
    suspend fun addNewVersion(versionRequest: VersionRequest,lang : String) : ApiResponse<Nothing>
    suspend fun getPinByVersion(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>

    suspend fun changePinVersion(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>

}