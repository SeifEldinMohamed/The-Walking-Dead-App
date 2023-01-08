package com.seif.thewalkingdeadapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
}