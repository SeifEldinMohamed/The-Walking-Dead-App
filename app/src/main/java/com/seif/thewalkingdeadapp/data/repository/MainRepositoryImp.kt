package com.seif.thewalkingdeadapp.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.seif.thewalkingdeadapp.data.mapper.toMyCharacter
import com.seif.thewalkingdeadapp.domain.model.MyCharacter
import com.seif.thewalkingdeadapp.domain.repository.DataStoreOperations
import com.seif.thewalkingdeadapp.domain.repository.MainRepository
import com.seif.thewalkingdeadapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepositoryImp @Inject constructor(
    private val dataStore: DataStoreOperations,
    private val remote: RemoteDataSource
) : MainRepository {

    fun getAllCharacters(): Flow<PagingData<MyCharacter>> {
        return remote.getAllHeroes().map {
            it.map { characterEntity ->
                characterEntity.toMyCharacter()
            }
        }
    }


    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

}