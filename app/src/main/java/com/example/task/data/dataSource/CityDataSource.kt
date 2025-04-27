package com.example.task.data.dataSource

import android.content.Context
import com.example.task.data.entity.CityEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class CityDataSource(
    private val trie: SearchTrieDataSource
) {
    private var cities: List<CityEntity>? = null

    fun loadAllData(context: Context): List<CityEntity> {
        cities?.let { return it }

        return try {
            val jsonFile = context.assets.open("cities.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<CityEntity>>() {}.type
            val entities: List<CityEntity> = Gson().fromJson(jsonFile, type)

            cities = entities.sortedBy { it.name }.also { sortedEntities ->
                sortedEntities.forEach { entity ->
                    trie.insert(entity)
                }
            }
            cities!!
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun search(prefix: String): List<CityEntity> =
        if (prefix.isBlank()) cities.orEmpty() else trie.filter(prefix)

}
