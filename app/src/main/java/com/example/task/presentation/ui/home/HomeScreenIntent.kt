package com.example.task.presentation.ui.home

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeScreenIntent {
    @Serializable
    data object GetHomeData : HomeScreenIntent
    @Serializable
    data class OnQueryChange(val query : String) : HomeScreenIntent
}
