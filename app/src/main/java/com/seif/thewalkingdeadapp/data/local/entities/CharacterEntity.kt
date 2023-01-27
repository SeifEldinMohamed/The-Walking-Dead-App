package com.seif.thewalkingdeadapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seif.thewalkingdeadapp.utils.Constants.CHARACTER_DATABASE_TABLE

@Entity(tableName = CHARACTER_DATABASE_TABLE)
data class CharacterEntity(
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