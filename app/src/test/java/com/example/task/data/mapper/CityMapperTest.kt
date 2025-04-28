package com.example.task.data.mapper

import com.example.task.data.entity.CityEntity
import com.example.task.data.entity.Coordinates
import com.example.task.data.model.CitiesGroupModel
import com.example.task.data.model.CityModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CityMapperTest {

 @Test
 fun `when mapping CityEntity with all fields, should map correctly to CityModel`() {
  // Given
  val cityEntity = listOf(
   CityEntity(
    country = "US",
    name = "New York",
    id = 1L,
    coordinates = Coordinates(
     longitude = -74.0060,
     latitude = 40.7128
    )
   )
  )

  // When
  val result = cityEntity.mapToCityModels()

  // Then
  assertThat(result).isEqualTo(
   listOf(
    CityModel(
     country = "US",
     name = "New York",
     id = 1L,
     longitude = -74.0060,
     latitude = 40.7128
    )
   )
  )
 }

 @Test
 fun `when mapping CityEntity with null fields, should map null values correctly`() {
  // Given
  val cityEntity = listOf(
   CityEntity(
    country = null,
    name = null,
    id = null,
    coordinates = null
   )
  )

  // When
  val result = cityEntity.mapToCityModels()

  // Then
  assertThat(result).isEqualTo(
   listOf(
    CityModel(
     country = null,
     name = null,
     id = null,
     longitude = null,
     latitude = null
    )
   )
  )
 }

 @Test
 fun `when mapping CityEntity with partial fields, should map available fields correctly`() {
  // Given
  val cityEntity = listOf(
   CityEntity(
    country = "UK",
    name = "London",
    id = 2L,
    coordinates = Coordinates(
     longitude = -0.1276,
     latitude = null
    )
   )
  )

  // When
  val result = cityEntity.mapToCityModels()

  // Then
  assertThat(result).isEqualTo(
   listOf(
    CityModel(
     country = "UK",
     name = "London",
     id = 2L,
     longitude = -0.1276,
     latitude = null
    )
   )
  )
 }

 @Test
 fun `when mapping CityEntity with empty coordinates, should map null coordinates`() {
  // Given
  val cityEntity = listOf(
   CityEntity(
    country = "FR",
    name = "Paris",
    id = 3L,
    coordinates = Coordinates(
     longitude = null,
     latitude = null
    )
   )
  )

  // When
  val result = cityEntity.mapToCityModels()

  // Then
  assertThat(result).isEqualTo(
   listOf(
    CityModel(
     country = "FR",
     name = "Paris",
     id = 3L,
     longitude = null,
     latitude = null
    )
   )
  )
 }

 @Test
 fun `when mapping CityEntity with only coordinates, should map coordinates correctly`() {
  // Given
  val cityEntity = listOf(
   CityEntity(
    country = null,
    name = null,
    id = null,
    coordinates = Coordinates(
     longitude = 13.4050,
     latitude = 52.5200
    )
   )
  )

  // When
  val result = cityEntity.mapToCityModels()

  // Then
  assertThat(result).isEqualTo(
   listOf(
    CityModel(
     country = null,
     name = null,
     id = null,
     longitude = 13.4050,
     latitude = 52.5200
    )
   )
  )
 }

 @Test
 fun `when mapping multiple CityEntities, should map all correctly`() {
  // Given
  val cityEntities = listOf(
   CityEntity(
    country = "US",
    name = "New York",
    id = 1L,
    coordinates = Coordinates(
     longitude = -74.0060,
     latitude = 40.7128
    )
   ),
   CityEntity(
    country = "UK",
    name = "London",
    id = 2L,
    coordinates = Coordinates(
     longitude = -0.1276,
     latitude = 51.5074
    )
   )
  )

  // When
  val result = cityEntities.mapToCityModels()

  // Then
  assertThat(result).isEqualTo(
   listOf(
    CityModel(
     country = "US",
     name = "New York",
     id = 1L,
     longitude = -74.0060,
     latitude = 40.7128
    ),
    CityModel(
     country = "UK",
     name = "London",
     id = 2L,
     longitude = -0.1276,
     latitude = 51.5074
    )
   )
  )
 }

 @Test
 fun `when grouping cities by first letter, should group correctly`() {
  // Given
  val cities = listOf(
   CityModel(name = "Amsterdam", country = "NL", id = 1L),
   CityModel(name = "Berlin", country = "DE", id = 2L),
   CityModel(name = "Boston", country = "US", id = 3L),
   CityModel(name = "Paris", country = "FR", id = 4L)
  )

  // When
  val result = cities.groupByFirstLetter()

  // Then
  assertThat(result).isEqualTo(
   mapOf(
    'A' to listOf(CityModel(name = "Amsterdam", country = "NL", id = 1L)),
    'B' to listOf(
     CityModel(name = "Berlin", country = "DE", id = 2L),
     CityModel(name = "Boston", country = "US", id = 3L)
    ),
    'P' to listOf(CityModel(name = "Paris", country = "FR", id = 4L))
   )
  )
 }

 @Test
 fun `when grouping cities with null names, should use # as key`() {
  // Given
  val cities = listOf(
   CityModel(name = "Amsterdam", country = "NL", id = 1L),
   CityModel(name = null, country = "DE", id = 2L),
   CityModel(name = "Boston", country = "US", id = 3L)
  )

  // When
  val result = cities.groupByFirstLetter()

  // Then
  assertThat(result).isEqualTo(
   mapOf(
    'A' to listOf(CityModel(name = "Amsterdam", country = "NL", id = 1L)),
    'B' to listOf(CityModel(name = "Boston", country = "US", id = 3L)),
    '#' to listOf(CityModel(name = null, country = "DE", id = 2L))
   )
  )
 }

 @Test
 fun `when converting grouped cities to sorted groups, should sort correctly`() {
  // Given
  val groupedCities = mapOf(
   'B' to listOf(
    CityModel(name = "Boston", country = "US", id = 3L),
    CityModel(name = "Berlin", country = "DE", id = 2L)
   ),
   'A' to listOf(CityModel(name = "Amsterdam", country = "NL", id = 1L)),
   'P' to listOf(CityModel(name = "Paris", country = "FR", id = 4L))
  )

  // When
  val result = groupedCities.toSortedGroups()

  // Then
  assertThat(result).isEqualTo(
   listOf(
    CitiesGroupModel(
     letter = "A",
     cities = listOf(CityModel(name = "Amsterdam", country = "NL", id = 1L))
    ),
    CitiesGroupModel(
     letter = "B",
     cities = listOf(
      CityModel(name = "Berlin", country = "DE", id = 2L),
      CityModel(name = "Boston", country = "US", id = 3L)
     )
    ),
    CitiesGroupModel(
     letter = "P",
     cities = listOf(CityModel(name = "Paris", country = "FR", id = 4L))
    )
   )
  )
 }

 @Test
 fun `when converting empty grouped cities to sorted groups, should return empty list`() {
  // Given
  val groupedCities = emptyMap<Char, List<CityModel>>()

  // When
  val result = groupedCities.toSortedGroups()

  // Then
  assertThat(result).isEmpty()
 }

 @Test
 fun `when converting city entities to grouped cities, should process correctly`() {
  // Given
  val cityEntities = listOf(
   CityEntity(
    name = "Amsterdam",
    country = "NL",
    id = 1L,
    coordinates = null
   ),
   CityEntity(
    name = "Berlin",
    country = "DE",
    id = 2L,
    coordinates = null
   ),
   CityEntity(
    name = "Boston",
    country = "US",
    id = 3L,
    coordinates = null
   )
  )

  // When
  val result = cityEntities.toGroupedCities()

  // Then
  assertThat(result).isEqualTo(
   listOf(
    CitiesGroupModel(
     letter = "A",
     cities = listOf(CityModel(name = "Amsterdam", country = "NL", id = 1L))
    ),
    CitiesGroupModel(
     letter = "B",
     cities = listOf(
      CityModel(name = "Berlin", country = "DE", id = 2L),
      CityModel(name = "Boston", country = "US", id = 3L)
     )
    )
   )
  )
 }

 @Test
 fun `when converting empty city entities to grouped cities, should return empty list`() {
  // Given
  val cityEntities = emptyList<CityEntity>()

  // When
  val result = cityEntities.toGroupedCities()

  // Then
  assertThat(result).isEmpty()
 }
}