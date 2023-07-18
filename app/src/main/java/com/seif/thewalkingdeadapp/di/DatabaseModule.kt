package com.seif.thewalkingdeadapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seif.thewalkingdeadapp.data.local.TheWalkingDeadDatabase
import com.seif.thewalkingdeadapp.utils.Constants.THE_WALKING_DEAD_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): RoomDatabase {
        return Room.databaseBuilder(
            context,
            TheWalkingDeadDatabase::class.java,
            THE_WALKING_DEAD_DATABASE
        ).build()
    }
}