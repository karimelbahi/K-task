package com.example.task.di

import android.app.Application
import com.example.task.data.dataSource.CityDataSource
import com.example.task.data.dataSource.SearchTrieDataSource
import com.example.task.data.repo.CitiesRepoImpl
import com.example.task.domain.repo.CitiesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Repositories {

    @Provides
    @Singleton
    fun provideCityDataSource(
        searchTrieDataSource: SearchTrieDataSource
    ): CityDataSource {
        return CityDataSource(searchTrieDataSource)
    }

    @Provides
    @Singleton
    fun provideCatsRepo(
        dataSource: CityDataSource,
        context : Application
    ): CitiesRepo {
        return CitiesRepoImpl(
            dataSource,
            context
        )
    }

}

