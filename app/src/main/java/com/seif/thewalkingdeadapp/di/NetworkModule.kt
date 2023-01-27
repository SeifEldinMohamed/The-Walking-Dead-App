package com.seif.thewalkingdeadapp.di

import androidx.paging.ExperimentalPagingApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.seif.thewalkingdeadapp.data.local.TheWalkingDeadDatabase
import com.seif.thewalkingdeadapp.data.remote.TheWalkingDeadApi
import com.seif.thewalkingdeadapp.data.remote.datasource.RemoteDataSourceImpl
import com.seif.thewalkingdeadapp.domain.repository.RemoteDataSource
import com.seif.thewalkingdeadapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS) // the read timeout is the timeout on waiting to read the data
            .connectTimeout(15, TimeUnit.SECONDS)// the connection timeout is the timeout in making the initial connection
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideTheWalkingDeadApi(retrofit: Retrofit): TheWalkingDeadApi {
        return retrofit.create(TheWalkingDeadApi::class.java)
    }



    @Provides
    @Singleton
    fun provideRemoteDataSource(
        walkingDeadApi: TheWalkingDeadApi,
        walkingDeadDatabase: TheWalkingDeadDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            walkingDeadApi = walkingDeadApi,
            walkingDeadDatabase = walkingDeadDatabase
        )
    }

}