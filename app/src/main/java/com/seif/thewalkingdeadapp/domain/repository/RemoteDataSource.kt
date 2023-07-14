package com.seif.thewalkingdeadapp.domain.repository

import androidx.paging.PagingData
import com.seif.thewalkingdeadapp.data.local.entities.CharacterEntity
import com.seif.thewalkingdeadapp.data.remote.dto.CharacterDto
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllCharacters(): Flow<PagingData<CharacterEntity>>
   // fun searchCharacters(query: String): Flow<PagingData<CharacterDto>>
}