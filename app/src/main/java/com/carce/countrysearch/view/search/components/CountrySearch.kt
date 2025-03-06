package com.carce.countrysearch.view.search.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carce.countrysearch.model.Country
import com.carce.countrysearch.viewmodel.CountryViewModel

@Composable
fun CountrySearch(
    title: String,
    viewModel: CountryViewModel
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val localFocusManager = LocalFocusManager.current

    CustomColumn(
        title = {
            TitleText(
                title = title,
                modifier = Modifier.padding(top = 35.dp, bottom = 20.dp)
            )
        },
        mainContent = {
                CountryNameSearch(
                    uiState.countryPrefix,
                    viewModel::onCountryNameSearch,
                    uiState.countries,
                    onCountrySelected = viewModel::onCountrySelected,
                    selectedCountry = uiState.selectedCountry,
                    focusManager = localFocusManager
                )
        },
    )
}

@Composable
private fun CountryNameSearch(
    prefix: String,
    onPrefixChanged: (String) -> Unit,
    countries: List<Country>,
    focusManager: FocusManager,
    onCountrySelected: (Country?) -> Unit,
    selectedCountry: Country?
) {
    EnterCountryName(
        prefix,
        onPrefixChanged,
        focusManager
    )
    ShowCountries(
        countries = countries,
        onCountrySelected = onCountrySelected,
        selectedCountry = selectedCountry
    )
}

@Composable
fun EnterCountryName(
    prefix: String,
    onPrefixChanged: (String) -> Unit,
    focusManager: FocusManager
) {

    Column(
    ) {
        TextFieldWithIconAndClear(
            modifier = Modifier
                .padding(bottom = 10.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            fieldValue = prefix,
            onChanged = onPrefixChanged,
            placeHolderValue = "Search",
            focusManager = focusManager
        )
    }

}

@Composable
fun ShowCountries(
    modifier: Modifier = Modifier,
    countries: List<Country>,
    onCountrySelected: (Country?) -> Unit,
    selectedCountry: Country?
) {

    LazyColumn(modifier = modifier) {
        itemsIndexed(items = countries) { index, country ->
            CountryListItem(country, onCountrySelected, selectedCountry)
        }
    }.takeUnless { countries.isEmpty() } ?: run {
        Column(
            Modifier.fillMaxWidth().padding(top = 30.dp).fillMaxHeight()
        ){
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp).align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}
