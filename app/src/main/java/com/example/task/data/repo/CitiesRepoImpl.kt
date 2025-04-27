package com.example.task.data.repo

import android.app.Application
import com.example.task.data.model.CitiesGroupModel
import com.example.task.data.model.QuerySearchRequest
import com.example.task.data.dataSource.CityDataSource
import com.example.task.domain.repo.CitiesRepo
import com.example.task.presentation.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesRepoImpl @Inject constructor(
    private val dataSource: CityDataSource,
    private val context: Application
) : CitiesRepo {

    override suspend fun getAllCities(): Flow<DataState<List<CitiesGroupModel>>> = flow {

        try {
            val result = dataSource.loadAllData(context)
            emit(DataState.Success(result.toGroupedCities()))
        } catch (e: Exception) {
            emit(DataState.Error(e.toString()))
        }
    }

    override suspend fun getQueryCities(request: QuerySearchRequest): Flow<DataState<List<CitiesGroupModel>>> = flow {
        try {
            val result = dataSource.search(request.query)
            emit(DataState.Success(result.toGroupedCities()))
        } catch (e: Exception) {
            emit(DataState.Error(e.toString()))
        }
    }
}
