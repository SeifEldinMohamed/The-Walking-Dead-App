package com.seif.thewalkingdeadapp.data.repository

import com.seif.thewalkingdeadapp.domain.repository.DataStoreOperations
import com.seif.thewalkingdeadapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImp @Inject constructor(
    private val dataStore: DataStoreOperations
): MainRepository {

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

}