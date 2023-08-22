package com.seif.thewalkingdeadapp.data.paging_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.seif.thewalkingdeadapp.data.local.TheWalkingDeadDatabase
import com.seif.thewalkingdeadapp.data.local.entities.CharacterEntity
import com.seif.thewalkingdeadapp.data.local.entities.CharacterRemoteKeysEntity
import com.seif.thewalkingdeadapp.data.mapper.toCharacterEntity
import com.seif.thewalkingdeadapp.data.mapper.toCharacterRemoteKeysEntity
import com.seif.thewalkingdeadapp.data.remote.TheWalkingDeadApi
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRemoteMediator @Inject constructor(
    private val walkingDeadApi: TheWalkingDeadApi,
    private val walkingDeadDatabase: TheWalkingDeadDatabase
) : RemoteMediator<Int, CharacterEntity>() { // key and value ( key: page of type integer(
    private val characterDao = walkingDeadDatabase.characterDao()
    private val characterRemoteKeysDao = walkingDeadDatabase.characterRemoteKeysDao()

    /**
     *      InitializeAction.SKIP_INITIAL_REFRESH // when the local data doesn't need to be refreshed
    InitializeAction.LAUNCH_INITIAL_REFRESH // when the local data needs to be fully refreshed
     **/
    override suspend fun initialize(): InitializeAction { // used to check if cached data is out of date and decide weather to trigger remote refresh.
        val currentTime = System.currentTimeMillis()
        val lastUpdated = characterRemoteKeysDao.getRemoteKeys(characterId = 1)?.lastUpdated ?: 0L
        val cacheTimeout =
            5 // how long we want to communicate with server ( when expired then we want to refresh data from our server )
        Log.d("RemoteMediator", "Current Time: ${parseMillis(currentTime)}")
        Log.d("RemoteMediator", "Last updated Time: ${parseMillis(lastUpdated)}")
        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            Log.d("RemoteMediator", "Up to Date")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.d("RemoteMediator", "Refresh")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }

    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
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
            if (response.characters.isNotEmpty()) {
                walkingDeadDatabase.withTransaction { // will allow us to execute multiple database operations, sequentially one by one
                    if (loadType == LoadType.REFRESH) { // happened in first time user enters app or if invalidate requested
                        characterDao.deleteAllCharacters()
                        characterRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    // get prev and next page from api response
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val lastUpdated = response.lastUpdated
                    val keys: List<CharacterRemoteKeysEntity> =
                        response.characters.map { characterDto ->
                            characterDto.toCharacterRemoteKeysEntity(prevPage, nextPage, lastUpdated)
                        }
                    // save in room database
                    characterRemoteKeysDao.addAllRemoteKeys(characterRemoteKeyEntities = keys)
                    characterDao.addCharacters(characterEntities = response.characters.map { it.toCharacterEntity() })
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                characterRemoteKeysDao.getRemoteKeys(characterId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()
            ?.let { characterDto ->
                characterRemoteKeysDao.getRemoteKeys(characterId = characterDto.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
            ?.let { characterDto ->
                characterRemoteKeysDao.getRemoteKeys(characterId = characterDto.id)
            }
    }

    private fun parseMillis(millis: Long): String {
        val date = Date(millis)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ROOT)
        return format.format(date)
    }
}
/**
 * anchorPosition: Most recently accessed index in the list, including placeholders.
null if no access in the PagingData has been made yet

 * closestItemToPosition(): This function can be called with anchorPosition to fetch
the loaded item that is closest to the last accessed index in the list.
 */