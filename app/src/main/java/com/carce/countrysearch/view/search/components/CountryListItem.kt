package com.carce.countrysearch.view.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.carce.countrysearch.model.Country

@Composable
fun CountryListItem(
    country: Country,
    onClick: (Country?) -> Unit,
    selectedCountry: Country?
) {
    Row (
        Modifier
            .fillMaxWidth(0.8f)
            .padding(top = 50.dp)
            .clickable { onClick(country) },

    ){
        AsyncImage(
            model = country.flags?.png,
            contentDescription = "${country.name?.common} flag",
            modifier = Modifier
                .padding(end = 16.dp)
        )
        Column {
            Text(
                country.name?.common.orEmpty(),
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            Text(
                country.name?.official.orEmpty(),
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
            Text(
                text = country.capital.takeUnless { it.isNullOrEmpty() }?.get(0) ?: "No Capital",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = Color.LightGray
            )
        }
    }
}
