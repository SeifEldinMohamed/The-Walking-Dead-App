package com.seif.thewalkingdeadapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seif.thewalkingdeadapp.utils.Constants.CHARACTER_REMOTE_KEYS_DATABASE_TABLE

@Entity(tableName = CHARACTER_REMOTE_KEYS_DATABASE_TABLE)
data class CharacterRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)