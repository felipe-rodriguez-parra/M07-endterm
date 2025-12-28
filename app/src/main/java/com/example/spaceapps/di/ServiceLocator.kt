package com.example.spaceapps.di

import android.content.Context
import androidx.room.Room
import com.example.spaceapps.data.api.SpaceXApiService
import com.example.spaceapps.data.local.RocketDao
import com.example.spaceapps.data.local.SpaceAppsDatabase
import com.example.spaceapps.data.repository.RocketRepository
import com.example.spaceapps.domain.usecase.GetRocketDetailUseCase
import com.example.spaceapps.domain.usecase.GetRocketsUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {
    private const val BASE_URL = "https://api.spacexdata.com/v4/"

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val client: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private val api: SpaceXApiService by lazy {
        retrofit.create(SpaceXApiService::class.java)
    }

    @Volatile
    private var database: SpaceAppsDatabase? = null

    private fun provideDatabase(context: Context): SpaceAppsDatabase {
        return database ?: synchronized(this) {
            database ?: Room.databaseBuilder(
                context.applicationContext,
                SpaceAppsDatabase::class.java,
                "spaceapps.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { database = it }
        }
    }

    private fun provideRocketDao(context: Context): RocketDao = provideDatabase(context).rocketDao()

    private fun provideRepository(context: Context): RocketRepository = RocketRepository(api, provideRocketDao(context))

    fun provideGetRocketsUseCase(context: Context): GetRocketsUseCase =
        GetRocketsUseCase(provideRepository(context))

    fun provideGetRocketDetailUseCase(context: Context): GetRocketDetailUseCase =
        GetRocketDetailUseCase(provideRepository(context))
}
