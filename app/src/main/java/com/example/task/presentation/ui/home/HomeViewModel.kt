package com.example.task.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.model.QuerySearchRequest
import com.example.task.domain.usecases.HomeUseCases
import com.example.task.presentation.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val CATS_LIST_NUM = 10
const val REQUEST_DELAY = 100L

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HomeState(),
    )

    fun onEvent(intent: HomeScreenIntent) {
        when (intent) {
            HomeScreenIntent.GetHomeData -> {
                _state.update { it.copy(loading = true) }
                getAllCities()
            }

            is HomeScreenIntent.OnQueryChange -> {
                _state.update { it.copy(loading = true, searchQuery = intent.query) }
                getQuerySearch(QuerySearchRequest(intent.query))
            }
        }
    }

    private fun getAllCities() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getCitiesUseCase.getAllCities().onEach { result ->
                when (result) {
                    is DataState.Error -> {
                        _state.update {
                            it.copy(
                                loading = false,
                                isSuccess = false,
                                errorMessage = result.error,
                                cities = emptyList(),
                                filteredCities = emptyList()
                            )
                        }
                    }

                    DataState.Loading -> {
                        _state.update { it.copy(loading = true) }
                    }

                    is DataState.Success -> {
                        _state.update {
                            it.copy(
                                loading = false,
                                isSuccess = true,
                                errorMessage = "",
                                cities = result.data
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getQuerySearch(request: QuerySearchRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getCitiesUseCase.getQueryCities(request).onEach { result ->
                when (result) {
                    is DataState.Error -> {
                        _state.update {
                            it.copy(
                                loading = false,
                                isSuccess = false,
                                errorMessage = result.error,
                                cities = emptyList(),
                                filteredCities = emptyList()
                            )
                        }
                    }

                    DataState.Loading -> {
                        _state.update { it.copy(loading = true) }
                    }

                    is DataState.Success -> {
                        _state.update {
                            it.copy(
                                loading = false,
                                isSuccess = true,
                                errorMessage = "",
                                filteredCities = result.data
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}


