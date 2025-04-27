package com.example.task.presentation.ui

import com.example.task.data.model.CitiesGroupModel
import com.example.task.data.model.CityModel

val mockCities = listOf(
    CityModel(
        id = 1,
        name = "Test City",
        country = "Test Country",
        latitude = 0.0,
        longitude = 0.0
    )
)
val mockCitiesGroup = listOf(
    CitiesGroupModel(
        letter = "T",
        cities = mockCities
    )
)

const val query = "test"
const val errorMessage = "Test error"