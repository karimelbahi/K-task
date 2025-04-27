package com.example.task.domain.usecases

import com.example.task.data.model.QuerySearchRequest
import com.example.task.domain.repo.CitiesRepo
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor (
    private val repository: CitiesRepo,
) {
    suspend fun getAllCities() = repository.getAllCities()

    suspend fun getQueryCities(request: QuerySearchRequest) = repository.getQueryCities(request)

}
