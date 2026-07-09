package com.fathersprophets.backend.models.superevent

import kotlinx.serialization.Serializable

@Serializable
data class SuperEventTeacher(
    val id : Int? = null,
    val name : String? = null,
    val image : String? = null
)