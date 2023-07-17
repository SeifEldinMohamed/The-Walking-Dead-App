package com.seif.thewalkingdeadapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable // to deserialize our response coming from server ( convert json to this ApiResponse )
data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val heroes: List<CharacterDto> = emptyList()
)