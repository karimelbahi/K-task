package com.example.task.data.mapper

import com.example.task.data.model.CitiesGroupModel
import com.example.task.data.model.CityModel
import com.example.task.data.entity.CityEntity

fun List<CityEntity>.toGroupedCities(): List<CitiesGroupModel> {
    return this
        .mapToCityModels()
        .groupByFirstLetter()
        .toSortedGroups()
}

fun List<CityEntity>.mapToCityModels(): List<CityModel> {
    return map { entity ->
        CityModel(
            country = entity.country,
            name = entity.name,
            id = entity.id,
            longitude = entity.coordinates?.longitude,
            latitude = entity.coordinates?.latitude,
        )
    }
}

fun List<CityModel>.groupByFirstLetter(): Map<Char, List<CityModel>> {
    return groupBy { city ->
        city.name?.firstOrNull()?.uppercaseChar() ?: '#'
    }
}

fun Map<Char, List<CityModel>>.toSortedGroups(): List<CitiesGroupModel> {
    return toSortedMap()
        .map { (letter, cities) ->
            CitiesGroupModel(
                letter = letter.toString(),
                cities = cities.sortedBy { it.name }
            )
        }
}