package com.seif.thewalkingdeadapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seif.thewalkingdeadapp.data.local.entities.Character

@Database(entities = [Character::class], version = 1)
@TypeConverters()
abstract class TheWalkingDeadDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}