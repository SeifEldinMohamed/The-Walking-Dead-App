package com.seif.thewalkingdeadapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.seif.thewalkingdeadapp.data.local.TheWalkingDeadDatabase
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKeysEntity
import com.seif.thewalkingdeadapp.data.mapper.toCharacter
import com.seif.thewalkingdeadapp.data.mapper.toCharacterRemoteKeys
import com.seif.thewalkingdeadapp.data.remote.TheWalkingDeadApi
import com.seif.thewalkingdeadapp.data.remote.dto.CharacterDto
import java.lang.Exception
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRemoteMediator @Inject constructor(
    private val walkingDeadApi: TheWalkingDeadApi,
    private val WalkingDeadDatabase: TheWalkingDeadDatabase
) : RemoteMediator<Int, CharacterDto>() {
    private val characterDao = WalkingDeadDatabase.characterDao()
    private val characterRemoteKeysDao = WalkingDeadDatabase.characterRemoteKeysDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterDto>
    ): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> { // get remote keys from databases table for the first item, return prev page
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
                LoadType.APPEND -> { // return next page
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
            }

            val response = walkingDeadApi.getAllCharacters(page = page)
            if (response.heroes.isNotEmpty()) {
                WalkingDeadDatabase.withTransaction { // will allow us to execute multiple database operations, sequentially one by one
                    if (loadType == LoadType.REFRESH) { // happened in first time user enters app or if invalidate requested
                        characterDao.deleteAllCharacters()
                        characterRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys: List<CharacterRemoteKeysEntity> =
                        response.heroes.map { it.toCharacterRemoteKeys(prevPage, nextPage) }
                    characterRemoteKeysDao.addAllRemoteKeys(characterRemoteKeyEntities = keys)
                    characterDao.addCharacters(characterEntities = response.heroes.map { it.toCharacter() })
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterDto>
    ): CharacterRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                characterRemoteKeysDao.getRemoteKeys(characterId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CharacterDto>
    ): CharacterRemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()
            ?.let { characterDto ->
                characterRemoteKeysDao.getRemoteKeys(characterId = characterDto.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CharacterDto>
    ): CharacterRemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
            ?.let { characterDto ->
                characterRemoteKeysDao.getRemoteKeys(characterId = characterDto.id)
            }
    }
}
/**
 * anchorPosition: Most recently accessed index in the list, including placeholders.
null if no access in the PagingData has been made yet

 * closestItemToPosition(): This function can be called with anchorPosition to fetch
the loaded item that is closest to the last accessed index in the list.
 */