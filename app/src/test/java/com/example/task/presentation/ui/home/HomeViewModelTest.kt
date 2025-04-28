package com.example.task.presentation.ui.home

import app.cash.turbine.test
import com.example.task.domain.usecases.HomeUseCases
import com.example.task.presentation.ui.utils.errorMessage
import com.example.task.presentation.ui.utils.mockCitiesGroup
import com.example.task.presentation.ui.utils.query
import com.example.task.presentation.utils.DataState
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var homeUseCases: HomeUseCases
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        homeUseCases = mockk(relaxed = true)
        viewModel = HomeViewModel(homeUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should have default values`() = runTest {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState.loading).isFalse()
            assertThat(initialState.isSuccess).isFalse()
            assertThat(initialState.searchQuery).isEmpty()
            assertThat(initialState.errorMessage).isNull()
            assertThat(initialState.cities).isEmpty()
            assertThat(initialState.filteredCities).isEmpty()
        }
    }

    @Test
    fun `when GetHomeData intent is sent, should call getAllCities and update state correctly`() = runTest {

        coEvery { homeUseCases.getCitiesUseCase.getAllCities() } returns flow {
            emit(DataState.Success(mockCitiesGroup))
        }

        viewModel.state.test {
            // Initial state
            awaitItem()

            // When
            viewModel.onEvent(HomeScreenIntent.GetHomeData)

            // Loading state
            val loadingState = awaitItem()
            assertThat(loadingState.loading).isTrue()

            // Success state
            val successState = awaitItem()
            assertThat(successState.isSuccess).isTrue()
            assertThat(successState.cities).isEqualTo(mockCitiesGroup)
            assertThat(successState.loading).isFalse()
        }

        coVerify { homeUseCases.getCitiesUseCase.getAllCities() }
    }

    @Test
    fun `when OnQueryChange intent is sent, should call getQueryCities and update state correctly`() = runTest {

        coEvery { homeUseCases.getCitiesUseCase.getQueryCities(any()) } returns flow {
            emit(DataState.Success(mockCitiesGroup))
        }

        viewModel.state.test {
            awaitItem() // initial state

            viewModel.onEvent(HomeScreenIntent.OnQueryChange(query))

            val loadingState = awaitItem()
            assertThat(loadingState.loading).isTrue()
            assertThat(loadingState.searchQuery).isEqualTo(query)

            val successState = awaitItem()
            assertThat(successState.isSuccess).isTrue()
            assertThat(successState.filteredCities).isEqualTo(mockCitiesGroup)
            assertThat(successState.loading).isFalse()
        }

        coVerify { homeUseCases.getCitiesUseCase.getQueryCities(any()) }
    }

    @Test
    fun `when error occurs during getAllCities, should update state with error`() = runTest {
        val errorMessage = "Test error"
        coEvery { homeUseCases.getCitiesUseCase.getAllCities() } returns flow {
            emit(DataState.Error(errorMessage))
        }

        viewModel.state.test {
            awaitItem() // initial state

            viewModel.onEvent(HomeScreenIntent.GetHomeData)

            val loadingState = awaitItem()
            assertThat(loadingState.loading).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.isSuccess).isFalse()
            assertThat(errorState.errorMessage).isEqualTo(errorMessage)
            assertThat(errorState.cities).isEmpty()
            assertThat(errorState.loading).isFalse()
        }
    }

    @Test
    fun `when error occurs during getQueryCities, should update state with error`() = runTest {
        coEvery { homeUseCases.getCitiesUseCase.getQueryCities(any()) } returns flow {
            emit(DataState.Error(errorMessage))
        }

        viewModel.state.test {
            awaitItem() // initial state

            viewModel.onEvent(HomeScreenIntent.OnQueryChange(query))

            val loadingState = awaitItem()
            assertThat(loadingState.loading).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.isSuccess).isFalse()
            assertThat(errorState.errorMessage).isEqualTo(errorMessage)
            assertThat(errorState.filteredCities).isEmpty()
            assertThat(errorState.loading).isFalse()
        }
    }
}