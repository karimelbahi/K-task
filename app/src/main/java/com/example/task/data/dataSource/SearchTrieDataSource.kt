package com.example.task.data.dataSource

import com.example.task.data.entity.CityEntity

class SearchTrieDataSource {

    private class TrieNode {
        val children: MutableMap<Char, TrieNode> = mutableMapOf()
        var isEnd: Boolean = false
        val cities: MutableList<CityEntity> = mutableListOf()
    }

    private val root = TrieNode()

    fun insert(city: CityEntity) {
        val key = city.name?.lowercase() ?: return
        val terminalNode = navigateToNode(key)
        markAsTerminal(terminalNode, city)
    }

    private fun navigateToNode(key: String): TrieNode {
        var currentNode = root
        for (char in key) {
            currentNode = currentNode.children.getOrPut(char) { TrieNode() }
        }
        return currentNode
    }

    private fun markAsTerminal(node: TrieNode, city: CityEntity) {
        node.isEnd = true
        node.cities.add(city)
    }

    fun filter(prefix: String): List<CityEntity> {
        val key = prefix.lowercase()
        val startNode = findNodeForPrefix(key) ?: return emptyList()
        return collectCitiesFromSubtree(startNode)
    }

    private fun findNodeForPrefix(prefix: String): TrieNode? {
        var currentNode = root
        for (char in prefix) {
            currentNode = currentNode.children[char] ?: return null
        }
        return currentNode
    }

    private fun collectCitiesFromSubtree(node: TrieNode): List<CityEntity> {
        val result = mutableListOf<CityEntity>()

        if (node.isEnd) {
            result.addAll(node.cities)
        }

        for (child in node.children.values) {
            result.addAll(collectCitiesFromSubtree(child))
        }

        return result
    }
}