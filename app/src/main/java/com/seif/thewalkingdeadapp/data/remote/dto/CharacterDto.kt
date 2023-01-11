package com.seif.thewalkingdeadapp.data.remote.dto

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
@Serializable
data class CharacterDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val realName: String,
    val about:String,
    val totalAppearances: Int,
    val image: String,
    val quote: String,
    val quoteTime: String,
    val rating: Double
)