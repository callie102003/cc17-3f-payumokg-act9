package com.lixoten.flightsearch.ui.screens.flight_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lixoten.flightsearch.data.MockData
import com.lixoten.flightsearch.model.Airport
import com.lixoten.flightsearch.model.Favorite

@Composable
fun FlightResults(
    modifier: Modifier = Modifier,
    departureAirport: Airport,
    destinationList: List<Airport>,
    favoriteList: List<Favorite>,
    onFavoriteClick: (String, String) -> Unit
) {
    Column {

        Text(
            text = "Result",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )


        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(destinationList, key = { it.id }) { item ->
                val isFavorite = favoriteList.find { f ->
                    f.departureCode == departureAirport.code &&
                            f.destinationCode == item.code }

                FlightRow(
                    isFavorite = isFavorite != null,
                    departureAirportCode = departureAirport.code,
                    departureAirportName = departureAirport.name,
                    destinationAirportCode = item.code,
                    destinationAirportName = item.name,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@Composable
fun FlightRow(
    isFavorite: Boolean,
    departureAirportCode: String,
    departureAirportName: String,
    destinationAirportCode: String,
    destinationAirportName: String,
    onFavoriteClick: (String, String) -> Unit
) {
    Card(
        backgroundColor = Color(0xFF001F54),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = "Arrival: $departureAirportCode - $departureAirportName",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Destination: $destinationAirportCode - $destinationAirportName",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Favorite Button
            Text(
                text = if (isFavorite) "★" else "☆",
                fontSize = 24.sp,
                color = if (isFavorite) Color.Yellow else Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        onFavoriteClick(departureAirportCode, destinationAirportCode)
                    }
            )
        }
    }
}

@Preview
@Composable
fun FlightResultsPreview() {
    val mockData = MockData.airports

    FlightResults(
        departureAirport = mockData[0],
        destinationList = mockData,
        favoriteList = emptyList(),
        onFavoriteClick = { _: String, _: String -> }
    )
}
