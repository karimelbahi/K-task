package com.example.task.data.repo

import android.app.Application
import app.cash.turbine.test
import com.example.task.data.dataSource.CityDataSource
import com.example.task.data.entity.CityEntity
import com.example.task.data.model.QuerySearchRequest
import com.example.task.presentation.ui.utils.MainDispatcherRule
import com.example.task.presentation.utils.DataState
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CitiesRepoImplTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: CitiesRepoImpl
    private val dataSource: CityDataSource = mockk()
    private val context: Application = mockk()
//    private val cityMapper: CityMapper = mockk()

    @Before
    fun setUp() {
        sut = CitiesRepoImpl(dataSource, context)
    }
    // TODO: I need to convert the extension functions to regular function or create CityMapper interface
//    @Test
//    fun `getAllCities when data source returns cities returns success`() = runTest {
//        // Given
//        val mockCityEntities = listOf(
//            CityEntity(
//                name = "New York",
//                country = "US",
//                id = 1L,
//                coordinates = null
//            ),
//            CityEntity(
//                name = "London",
//                country = "UK",
//                id = 2L,
//                coordinates = null
//            )
//        )
//
//        val mockCityModels = listOf(
//            CityModel(
//                name = "New York",
//                country = "US",
//                id = 1L,
//                longitude = null,
//                latitude = null
//            ),
//            CityModel(
//                name = "London",
//                country = "UK",
//                id = 2L,
//                longitude = null,
//                latitude = null
//            )
//        )
//        val mockGroupedCities = listOf(
//            CitiesGroupModel(
//                letter = "N",
//                cities = listOf(mockCityModels[0])
//            ),
//            CitiesGroupModel(
//                letter = "L",
//                cities = listOf(mockCityModels[1])
//            )
//        )
//        coEvery { dataSource.loadAllData(context) } returns mockCityEntities
//        every { mockCityEntities.toGroupedCities () } returns mockGroupedCities
//
//        // When
//        val result = sut.getAllCities()
//
//        // Then
//        result.test {
//            val loading = awaitItem()
//            assertThat(loading).isEqualTo(DataState.Loading)
//
//            val success = awaitItem()
//            assertThat(success).isEqualTo(DataState.Success(mockGroupedCities))
//
//            awaitComplete()
//        }
//    }

    @Test
    fun `getAllCities when data source throws exception returns error`() = runTest {
        // Given
        val exception = Exception("Test error")
        coEvery { dataSource.loadAllData(context) } throws exception

        // When
        val result = sut.getAllCities()

        // Then
        result.test {
            val error = awaitItem()
            assertThat(error).isEqualTo(DataState.Error(exception.toString()))

            awaitComplete()
        }
    }

    // TODO: I need to convert the extension functions to regular function or create CityMapper interface
//    @Test
//    fun `getQueryCities when data source returns cities returns success`() = runTest {
//        // Given
//        val mockCityEntities = listOf(
//            CityEntity(
//                name = "New York",
//                country = "US",
//                id = 1L,
//                coordinates = null
//            ),
//            CityEntity(
//                name = "London",
//                country = "UK",
//                id = 2L,
//                coordinates = null
//            )
//        )
//
//        val mockCityModels = listOf(
//            CityModel(
//                name = "New York",
//                country = "US",
//                id = 1L,
//                longitude = null,
//                latitude = null
//            ),
//            CityModel(
//                name = "London",
//                country = "UK",
//                id = 2L,
//                longitude = null,
//                latitude = null
//            )
//        )
//        val mockGroupedCities = listOf(
//            CitiesGroupModel(
//                letter = "N",
//                cities = listOf(mockCityModels[0])
//            ),
//            CitiesGroupModel(
//                letter = "L",
//                cities = listOf(mockCityModels[1])
//            )
//        )
//        val queryRequest = QuerySearchRequest(query = "New York")
//
//        coEvery { dataSource.search(queryRequest.query) } returns mockCityEntities
//        every { mockCityEntities.toGroupedCities () } returns mockGroupedCities
//
//        // When
//        val result = sut.getQueryCities(queryRequest)
//
//        // Then
//        result.test {
////            val loading = awaitItem()
////            assertThat(loading).isEqualTo(DataState.Loading)
//
//            val success = awaitItem()
//            assertThat(success).isEqualTo(DataState.Success(mockCityModels))
//
//            awaitComplete()
//        }
//    }

    @Test
    fun `getQueryCities when data source returns empty list returns success with empty list`() = runTest {
        // Given
        val mockCityEntities = emptyList<CityEntity>()
        val queryRequest = QuerySearchRequest(query = "NonExistentCity")

        coEvery { dataSource.search(queryRequest.query) } returns mockCityEntities

        // When
        val result = sut.getQueryCities(queryRequest)

        // Then
        result.test {
            val success = awaitItem()
            assertThat(success).isEqualTo(DataState.Success(emptyList<CityEntity>()))

            awaitComplete()
        }
    }

    @Test
    fun `getQueryCities when data source throws exception returns error`() = runTest {
        // Given
        val exception = Exception("Test error")
        val queryRequest = QuerySearchRequest(query = "New York")

        coEvery { dataSource.search(queryRequest.query) } throws exception

        // When
        val result = sut.getQueryCities(queryRequest)

        // Then
        result.test {
            val error = awaitItem()
            assertThat(error).isEqualTo(DataState.Error(exception.toString()))

            awaitComplete()
        }
    }
}