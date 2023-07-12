package com.seif.thewalkingdeadapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.seif.thewalkingdeadapp.data.local.TheWalkingDeadDatabase
import com.seif.thewalkingdeadapp.data.local.entities.CharacterEntity
import com.seif.thewalkingdeadapp.data.paging_source.CharacterRemoteMediator
import com.seif.thewalkingdeadapp.data.remote.TheWalkingDeadApi
import com.seif.thewalkingdeadapp.domain.repository.RemoteDataSource
import com.seif.thewalkingdeadapp.utils.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow


@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val walkingDeadApi: TheWalkingDeadApi,
    private val walkingDeadDatabase: TheWalkingDeadDatabase
) : RemoteDataSource {

    private val heroDao = walkingDeadDatabase.characterDao()

    override fun getAllCharacters(): Flow<PagingData<CharacterEntity>> {
        val pagingSourceFactory = { heroDao.getAllCharacters() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = CharacterRemoteMediator(
                walkingDeadApi = walkingDeadApi,
                walkingDeadDatabase = walkingDeadDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

//    override fun searchCharacters(query: String): Flow<PagingData<CharacterDomainModel>> {
//        return Pager(
//            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
//            pagingSourceFactory = {
//               // SearchHeroesSource(borutoApi = walkingDeadApi, query = query)
//            }
//        ).flow
//    }
}