package com.podplay.android.di

import android.content.Context
import com.podplay.android.db.PodPlayDatabase
import com.podplay.android.db.PodcastDao
import com.podplay.android.repository.ItunesRepo
import com.podplay.android.repository.ItunesRepoImpl
import com.podplay.android.repository.PodcastRepo
import com.podplay.android.repository.PodcastRepoImpl
import com.podplay.android.service.ItunesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit) : ItunesService =
        retrofit.create(ItunesService::class.java)

    @Singleton
    @Provides
    fun providePodPlayDatabase(@ApplicationContext context: Context) = PodPlayDatabase.getInstance(context)

    @Provides
    fun providePodcastDao(podPlayDb: PodPlayDatabase) : PodcastDao = podPlayDb.podcastDao()

    @Provides
    fun provideItunesRepo(itunesRepoImpl: ItunesRepoImpl) : ItunesRepo = itunesRepoImpl

    @Provides
    fun providePodcastRepo(podcastRepoImpl: PodcastRepoImpl) : PodcastRepo = podcastRepoImpl

}