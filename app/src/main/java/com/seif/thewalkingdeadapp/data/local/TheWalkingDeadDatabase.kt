package com.seif.thewalkingdeadapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seif.thewalkingdeadapp.data.local.dao.CharacterDao
import com.seif.thewalkingdeadapp.data.local.dao.CharacterRemoteKeysDao
import com.seif.thewalkingdeadapp.data.local.entities.CharacterEntity
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKeysEntity

@Database(entities = [CharacterEntity::class, CharacterRemoteKeysEntity::class], version = 1)
@TypeConverters()
abstract class TheWalkingDeadDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeysDao(): CharacterRemoteKeysDao
}