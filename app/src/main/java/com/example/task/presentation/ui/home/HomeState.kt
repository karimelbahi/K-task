package com.example.task.presentation.ui.home

import com.example.task.data.model.CitiesGroupModel


data class HomeState(
    val loading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    var searchQuery: String = "",
    val cities: List<CitiesGroupModel> =  emptyList(),
    val filteredCities: List<CitiesGroupModel> =  emptyList(),
)
