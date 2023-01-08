package com.seif.thewalkingdeadapp.di

import android.content.Context
import com.seif.thewalkingdeadapp.data.local.pref.DataStoreOperationsImp
import com.seif.thewalkingdeadapp.domain.repository.DataStoreOperations
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

}