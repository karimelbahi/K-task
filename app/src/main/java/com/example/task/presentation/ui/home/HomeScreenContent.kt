package com.example.task.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task.R
import com.example.task.common.components.LoadingDialog
import com.example.task.common.components.MySpace16
import com.example.task.common.utils.hideKeyboard
import com.example.task.common.utils.openGoogleMaps
import com.example.task.data.model.CityModel
import com.example.task.presentation.ui.home.components.CardListSection
import com.example.task.presentation.ui.home.components.HeaderTitle
import com.example.task.presentation.ui.home.components.LetterHeader
import com.example.task.presentation.ui.home.components.SearchBarSection

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    stateValue: HomeState,
    onQueryChange: (String) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f0f5))
            .pointerInput(Unit) {
                detectTapGestures(onTap = { context.hideKeyboard() })
            }
    ) {
        HeaderTitle()
        val citiesNum = if (stateValue.filteredCities.isNotEmpty()) {
            stateValue.filteredCities.sumOf { it.cities.size }
        } else {
            stateValue.cities.sumOf { it.cities.size }
        }

        Text(
            text = stringResource(R.string.cities, citiesNum),
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        if (stateValue.loading) {
            LoadingDialog()
        }

        if (stateValue.isSuccess) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .imePadding()
                    .consumeWindowInsets(WindowInsets.ime),
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    val cities = stateValue.filteredCities.ifEmpty { stateValue.cities }
                    cities.forEach { group ->
                        stickyHeader {
                            LetterHeader(letter = group.letter.orEmpty())
                        }

                        items(group.cities.size) {
                            CardListSection(group.cities[it], cities.last().cities.last().id)
                        }
                    }
                }
            }

            SearchBarSection(
                query = stateValue.searchQuery,
                onValueChange = onQueryChange
            )
        }
    }
}

