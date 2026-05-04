package com.fathersprophets.backend.services.version

import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.dto.version.AdminPinRequest
import com.fathersprophets.backend.models.dto.version.VersionRequest
import com.fathersprophets.backend.models.dto.version.VersionResponse

interface IVersionService {

    suspend fun getLastVersion(lang : String) : ApiResponse<VersionResponse>
    suspend fun addNewVersion(versionRequest: VersionRequest,lang : String) : ApiResponse<Nothing>
    suspend fun getPinByVersion(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>

    suspend fun changePinVersion(adminPinRequest: AdminPinRequest,lang : String) : ApiResponse<Nothing>

}