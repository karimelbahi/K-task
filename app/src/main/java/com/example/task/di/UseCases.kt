package com.example.task.di

import com.example.task.domain.repo.CitiesRepo
import com.example.task.domain.usecases.GetCitiesUseCase
import com.example.task.domain.usecases.HomeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCases {

    @Provides
    @Singleton
    fun provideHomeUseCases(citiesRepo: CitiesRepo): HomeUseCases{
        return HomeUseCases(getCitiesUseCase = GetCitiesUseCase(citiesRepo))
    }

}