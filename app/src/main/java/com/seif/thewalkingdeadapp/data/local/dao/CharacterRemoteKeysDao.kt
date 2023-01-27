package com.seif.thewalkingdeadapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKeysEntity

@Dao
interface CharacterRemoteKeysDao {

    @Query("SELECT * FROM character_remote_keys_table WHERE id = :characterId")
    suspend fun getRemoteKeys(characterId: Int): CharacterRemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(characterRemoteKeyEntities: List<CharacterRemoteKeysEntity>)

    @Query("DELETE FROM character_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}