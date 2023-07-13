package com.seif.thewalkingdeadapp.di

import android.content.Context
import com.seif.thewalkingdeadapp.data.local.pref.DataStoreOperationsImp
import com.seif.thewalkingdeadapp.data.repository.MainRepositoryImp
import com.seif.thewalkingdeadapp.domain.repository.DataStoreOperations
import com.seif.thewalkingdeadapp.domain.repository.MainRepository
import com.seif.thewalkingdeadapp.domain.repository.RemoteDataSource
import com.seif.thewalkingdeadapp.domain.usecase.GetAllCharactersUseCase
import com.seif.thewalkingdeadapp.domain.usecase.ReadOnBoardingUseCase
import com.seif.thewalkingdeadapp.domain.usecase.SaveOnBoardingUseCase
import com.seif.thewalkingdeadapp.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImp(context = context)
    }

    @Provides
    @Singleton
    fun provideMainRepository(
        dataStoreOperations: DataStoreOperations,
        remote: RemoteDataSource
    ): MainRepository {
        return MainRepositoryImp(dataStoreOperations, remote)
    }


    @Provides
    @Singleton
    fun provideUseCases(mainRepository: MainRepository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(mainRepository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(mainRepository),
            getAllCharactersUseCase = GetAllCharactersUseCase(mainRepository)
        )
    }
}