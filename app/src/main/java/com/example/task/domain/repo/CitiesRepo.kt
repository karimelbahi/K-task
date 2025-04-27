package com.example.task.domain.repo

import com.example.task.data.model.CitiesGroupModel
import com.example.task.data.model.QuerySearchRequest
import com.example.task.presentation.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface CitiesRepo {
    suspend fun getAllCities(): Flow<DataState<List<CitiesGroupModel>>>
    suspend fun getQueryCities(request: QuerySearchRequest): Flow<DataState<List<CitiesGroupModel>>>
}