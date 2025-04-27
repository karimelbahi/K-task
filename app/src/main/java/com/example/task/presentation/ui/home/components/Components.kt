package com.example.task.presentation.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task.R
import com.example.task.common.components.MySpace16
import com.example.task.common.utils.openGoogleMaps
import com.example.task.data.model.CityModel

@Composable
fun HeaderTitle() {
    Text(
        text = stringResource(R.string.city_search),
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
        color = Color(0xFF3D3C3C),
        modifier = Modifier.padding(bottom = 16.dp, top = 8.dp, start = 8.dp)
    )
}

@Composable
fun LetterHeader(letter: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(0.5.dp, Color.Gray, CircleShape)
        ) {
            Text(
                text = letter,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun CardListSection(city: CityModel, index: Long?) {
    val context = LocalContext.current
    var cardHeight by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 35.dp, end = 10.dp)
    ) {
        TimelineConnector(cardHeight = cardHeight, isLastItem = city.id == index)

        CityCard(
            city = city,
            onCardClick = {
                if (city.latitude != null && city.longitude != null) {
                    context.openGoogleMaps(city.latitude, city.longitude)
                }
            },
            onHeightMeasured = { height -> cardHeight = height }
        )
    }
}

@Composable
fun TimelineConnector(cardHeight: Int, isLastItem: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(with(LocalDensity.current) { cardHeight.toDp() + 8.dp })
                .background(Color.LightGray)
        )
        if (isLastItem) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            MySpace16()
        }
    }
}

@Composable
fun CityCard(
    city: CityModel,
    onCardClick: () -> Unit,
    onHeightMeasured: (Int) -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, bottom = 8.dp)
            .onGloballyPositioned { coordinates ->
                onHeightMeasured(coordinates.size.height)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CountryCircle(country = city.country)

            CityDetails(
                cityName = city.name,
                country = city.country,
                latitude = city.latitude,
                longitude = city.longitude
            )
        }
    }
}

@Composable
fun CountryCircle(country: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .width(70.dp)
            .height(70.dp)
            .background(Color(0xFFEEEEEE))
    ) {
        Text(
            text = country.orEmpty(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 5.dp)
        )
    }
}

@Composable
fun CityDetails(
    cityName: String?,
    country: String?,
    latitude: Double?,
    longitude: Double?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
    ) {
        Text(
            text = "$cityName, $country",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Text(
            text = "$latitude, $longitude",
            color = Color.DarkGray,
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SearchBarSection(
    query: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        SearchField(query = query, onValueChange = onValueChange)
    }
}

@Composable
fun SearchField(
    query: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        placeholder = {
            Text(
                stringResource(R.string.searching),
                color = Color(0xFFb3b3b3)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color(0xFFebebeb), shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFFb3b3b3),
                modifier = Modifier.padding(4.dp)
            )
        },
        textStyle = TextStyle(
            fontSize = 12.sp,
            lineHeight = 12.sp
        ),

        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color(0xFFEEEEEE),
            unfocusedContainerColor = Color(0xFFEEEEEE),
            disabledContainerColor = Color(0xFFEEEEEE)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HeaderTitlePreview() {
    MaterialTheme {
        HeaderTitle()
    }
}

@Preview(showBackground = true)
@Composable
fun LetterHeaderPreview() {
    MaterialTheme {
        LetterHeader(letter = "A")
    }
}

@Preview(showBackground = true)
@Composable
fun CardListSectionPreview() {
    val sampleCity = CityModel(
        id = 1L,
        name = "New York",
        country = "US",
        latitude = 40.7128,
        longitude = -74.0060
    )

    MaterialTheme {
        CardListSection(city = sampleCity, index = 3L)
    }
}

@Preview(showBackground = true)
@Composable
fun TimelineConnectorPreview() {
    MaterialTheme {
        TimelineConnector(cardHeight = 150, isLastItem = false)
    }
}

@Preview(showBackground = true)
@Composable
fun CityCardPreview() {
    val sampleCity = CityModel(
        id = 1L,
        name = "Paris",
        country = "FR",
        latitude = 48.8566,
        longitude = 2.3522
    )

    MaterialTheme {
        CityCard(
            city = sampleCity,
            onCardClick = {},
            onHeightMeasured = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountryCirclePreview() {
    MaterialTheme {
        CountryCircle(country = "JP")
    }
}

@Preview(showBackground = true)
@Composable
fun CityDetailsPreview() {
    MaterialTheme {
        CityDetails(
            cityName = "Tokyo",
            country = "JP",
            latitude = 35.6762,
            longitude = 139.6503
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarSectionPreview() {
    MaterialTheme {
        SearchBarSection(
            query = "Berlin",
            onValueChange = {}
        )
    }
}
