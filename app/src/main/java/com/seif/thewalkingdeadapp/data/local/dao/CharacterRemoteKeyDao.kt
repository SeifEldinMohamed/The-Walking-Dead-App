package com.seif.thewalkingdeadapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKey

@Dao
interface CharacterRemoteKeyDao {

    @Query("SELECT * FROM character_remote_key_table WHERE id = :id")
    suspend fun getRemoteKey(id: Int): CharacterRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(characterRemoteKeys: List<CharacterRemoteKey>)

    @Query("DELETE FROM character_remote_key_table")
    suspend fun deleteAllRemoteKeys()

}