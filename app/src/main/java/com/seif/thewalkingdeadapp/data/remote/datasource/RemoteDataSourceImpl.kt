package com.seif.thewalkingdeadapp.data.remote.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.seif.thewalkingdeadapp.data.local.TheWalkingDeadDatabase
import com.seif.thewalkingdeadapp.data.local.entities.CharacterEntity
import com.seif.thewalkingdeadapp.data.paging_source.CharacterRemoteMediator
import com.seif.thewalkingdeadapp.data.remote.TheWalkingDeadApi
import com.seif.thewalkingdeadapp.data.remote.dto.CharacterDto
import com.seif.thewalkingdeadapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val walkingDeadApi: TheWalkingDeadApi,
    private val walkingDeadDatabase: TheWalkingDeadDatabase
) : RemoteDataSource {

    private val characterDao = walkingDeadDatabase.characterDao()

    override fun getAllHeroes(): Flow<PagingData<CharacterEntity>> {
        val pagingSourceFactory = { characterDao.getAllCharacters() }
        return Pager(
            config = PagingConfig(pageSize = 3),
            remoteMediator = CharacterRemoteMediator(
                walkingDeadApi = walkingDeadApi,
                walkingDeadDatabase = walkingDeadDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<CharacterDto>> {
//        return Pager(
//            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
//            pagingSourceFactory = {
//                SearchHeroesSource(borutoApi = walkingDeadApi, query = query)
//            }
//        ).flow
        TODO()
    }
}