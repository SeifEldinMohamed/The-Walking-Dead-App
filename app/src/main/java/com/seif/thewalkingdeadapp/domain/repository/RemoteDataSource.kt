package com.seif.thewalkingdeadapp.domain.repository

import androidx.paging.PagingData
import com.seif.thewalkingdeadapp.data.local.entities.CharacterEntity
import com.seif.thewalkingdeadapp.data.remote.dto.CharacterDto
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllHeroes(): Flow<PagingData<CharacterEntity>>
    fun searchHeroes(query: String): Flow<PagingData<CharacterDto>>
}