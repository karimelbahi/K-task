package com.example.task.di

import com.example.task.data.dataSource.SearchTrieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSources {
    @Provides
    @Singleton
    fun provideSearchTrieDataSource(): SearchTrieDataSource {
        return SearchTrieDataSource()
    }
}