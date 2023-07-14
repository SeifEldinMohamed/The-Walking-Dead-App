package com.seif.thewalkingdeadapp.domain.repository

import androidx.paging.PagingData
import com.seif.thewalkingdeadapp.domain.model.CharacterDomainModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
    fun getAllCharacters(): Flow<PagingData<CharacterDomainModel>>
}