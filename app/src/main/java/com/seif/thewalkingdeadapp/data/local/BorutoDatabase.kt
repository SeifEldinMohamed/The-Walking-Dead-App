package com.seif.thewalkingdeadapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seif.thewalkingdeadapp.data.local.dao.CharacterDao
import com.seif.thewalkingdeadapp.data.local.dao.CharacterRemoteKeyDao
import com.seif.thewalkingdeadapp.data.local.entities.Character
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKey

@Database(entities = [Character::class, CharacterRemoteKey::class], version = 1)
@TypeConverters()
abstract class TheWalkingDeadDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeyDao(): CharacterRemoteKeyDao
}