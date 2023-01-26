package com.seif.thewalkingdeadapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.seif.thewalkingdeadapp.data.local.TheWalkingDeadDatabase
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKeys
import com.seif.thewalkingdeadapp.data.mapper.toCharacter
import com.seif.thewalkingdeadapp.data.mapper.toCharacterRemoteKeys
import com.seif.thewalkingdeadapp.data.remote.TheWalkingDeadApi
import com.seif.thewalkingdeadapp.data.remote.dto.CharacterDto
import java.lang.Exception
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRemoteMediator @Inject constructor(
    private val walkingDeadApi: TheWalkingDeadApi,
    private val database: TheWalkingDeadDatabase
) : RemoteMediator<Int, CharacterDto>() {
    private val characterDao = database.characterDao()
    private val characterRemoteKeys = database.characterRemoteKeysDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterDto>
    ): MediatorResult {
        return try {
            val response = walkingDeadApi.getAllCharacters()
            if (response.heroes.isNotEmpty()) {
                database.withTransaction { // will allow us to execute multiple database operations, sequentially one by one
                    if (loadType == LoadType.REFRESH) { // happened in first time user enters app or if invalidate requested
                        characterDao.deleteAllCharacters()
                        characterRemoteKeys.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys: List<CharacterRemoteKeys> = response.heroes.map { it.toCharacterRemoteKeys(prevPage, nextPage) }
                    characterRemoteKeys.addAllRemoteKeys(characterRemoteKeys = keys)
                    characterDao.addCharacters(characters = response.heroes.map { it.toCharacter() })
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}