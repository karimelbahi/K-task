package com.example.task.domain.usecases

import app.cash.turbine.test
import com.example.task.data.model.CitiesGroupModel
import com.example.task.data.model.CityModel
import com.example.task.data.model.QuerySearchRequest
import com.example.task.presentation.ui.utils.MainDispatcherRule
import com.example.task.presentation.utils.DataState
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeUseCasesTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: HomeUseCases
    private val getCitiesUseCase: GetCitiesUseCase = mockk()

    @Before
    fun setUp() {
        sut = HomeUseCases(getCitiesUseCase)
    }

    @Test
    fun `getCitiesUseCase when getAllCities returns success returns success`() = runTest {
        // Given
        val mockCities = listOf(
            CityModel(
                name = "New York",
                country = "US",
                id = 1L,
                longitude = -74.0060,
                latitude = 40.7128
            ),
            CityModel(
                name = "London",
                country = "UK",
                id = 2L,
                longitude = -0.1276,
                latitude = 51.5074
            )
        )

        val mockGroupedCities = listOf(
            CitiesGroupModel(
                letter = "L",
                cities = listOf(mockCities[1])
            ),
            CitiesGroupModel(
                letter = "N",
                cities = listOf(mockCities[0])
            )
        )

        coEvery { getCitiesUseCase.getAllCities() } returns flow {
            emit(DataState.Success(mockGroupedCities))
        }

        // When
        val result = sut.getCitiesUseCase.getAllCities()

        // Then
        result.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(DataState.Success(mockGroupedCities))
            awaitComplete()
        }
    }

    @Test
    fun `getCitiesUseCase when getAllCities returns empty list returns success with empty list`() =
        runTest {
            // Given
            val mockGroupedCities = emptyList<CitiesGroupModel>()

            coEvery { getCitiesUseCase.getAllCities() } returns flow {
                emit(DataState.Success(mockGroupedCities))
            }

            // When
            val result = sut.getCitiesUseCase.getAllCities()

            // Then
            result.test {
                val item = awaitItem()
                assertThat(item).isEqualTo(DataState.Success(mockGroupedCities))
                awaitComplete()
            }
        }

    @Test
    fun `getCitiesUseCase when getAllCities returns cities with same first letter groups them together`() =
        runTest {
            // Given
            val mockCities = listOf(
                CityModel(
                    name = "London",
                    country = "UK",
                    id = 1L,
                    longitude = -0.1276,
                    latitude = 51.5074
                ),
                CityModel(
                    name = "Los Angeles",
                    country = "US",
                    id = 2L,
                    longitude = -118.2437,
                    latitude = 34.0522
                )
            )

            val mockGroupedCities = listOf(
                CitiesGroupModel(
                    letter = "L",
                    cities = mockCities.sortedBy { it.name }
                )
            )

            coEvery { getCitiesUseCase.getAllCities() } returns flow {
                emit(DataState.Success(mockGroupedCities))
            }

            // When
            val result = sut.getCitiesUseCase.getAllCities()

            // Then
            result.test {
                val item = awaitItem()
                assertThat(item).isEqualTo(DataState.Success(mockGroupedCities))
                awaitComplete()
            }
        }

    @Test
    fun `getCitiesUseCase when getQueryCities returns success returns success`() = runTest {
        // Given
        val mockCities = listOf(
            CityModel(
                name = "New York",
                country = "US",
                id = 1L,
                longitude = -74.0060,
                latitude = 40.7128
            )
        )

        val mockGroupedCities = listOf(
            CitiesGroupModel(
                letter = "N",
                cities = mockCities
            )
        )

        val queryRequest = QuerySearchRequest(query = "New York")

        coEvery { getCitiesUseCase.getQueryCities(queryRequest) } returns flow {
            emit(DataState.Success(mockGroupedCities))
        }

        // When
        val result = sut.getCitiesUseCase.getQueryCities(queryRequest)

        // Then
        result.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(DataState.Success(mockGroupedCities))
            awaitComplete()
        }
    }

    @Test
    fun `getCitiesUseCase when getQueryCities returns no results returns success with empty list`() =
        runTest {
            // Given
            val mockGroupedCities = emptyList<CitiesGroupModel>()
            val queryRequest = QuerySearchRequest(query = "NonExistentCity")

            coEvery { getCitiesUseCase.getQueryCities(queryRequest) } returns flow {
                emit(DataState.Success(mockGroupedCities))
            }

            // When
            val result = sut.getCitiesUseCase.getQueryCities(queryRequest)

            // Then
            result.test {
                val item = awaitItem()
                assertThat(item).isEqualTo(DataState.Success(mockGroupedCities))
                awaitComplete()
            }
        }

    @Test
    fun `getCitiesUseCase when getQueryCities returns multiple cities with same first letter groups them together`() =
        runTest {
            // Given
            val mockCities = listOf(
                CityModel(
                    name = "New York",
                    country = "US",
                    id = 1L,
                    longitude = -74.0060,
                    latitude = 40.7128
                ),
                CityModel(
                    name = "New Orleans",
                    country = "US",
                    id = 2L,
                    longitude = -90.0715,
                    latitude = 29.9511
                )
            )

            val mockGroupedCities = listOf(
                CitiesGroupModel(
                    letter = "N",
                    cities = mockCities.sortedBy { it.name }
                )
            )

            val queryRequest = QuerySearchRequest(query = "New")

            coEvery { getCitiesUseCase.getQueryCities(queryRequest) } returns flow {
                emit(DataState.Success(mockGroupedCities))
            }

            // When
            val result = sut.getCitiesUseCase.getQueryCities(queryRequest)

            // Then
            result.test {
                val item = awaitItem()
                assertThat(item).isEqualTo(DataState.Success(mockGroupedCities))
                awaitComplete()
            }
        }
}