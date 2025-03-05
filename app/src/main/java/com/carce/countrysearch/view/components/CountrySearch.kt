package com.carce.countrysearch.view.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carce.countrysearch.model.dto.Country
import com.carce.countrysearch.viewmodel.CountryViewModel
import com.carce.countrysearch.viewmodel.SearchUiState

@Composable
fun CountrySearch(
    title: String,
    modifier: Modifier = Modifier,
    viewModel: CountryViewModel
) {

    val uiState = viewModel.uiState.collectAsState(SearchUiState()).value
    viewModel.getCountries()
    val localFocusManager = LocalFocusManager.current

    CompactLayoutWithScaffold(
        title = {
            TitleText(
                title = title,
                modifier = modifier.padding(top = 35.dp, bottom = 20.dp)
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
        allowScroll = false,
    )
}

@Composable
private fun CountryNameSearch(
    prefix: String,
    onPrefixChanged: (String) -> Unit,
    countries: List<Country>,
    focusManager: FocusManager,
    onCountrySelected: (Country) -> Unit,
    selectedCountry: Country?
) {
    EnterCountryName(
        prefix,
        onPrefixChanged,
        focusManager
    )
    ShowCountries(
        countries = countries,
        onCitySelected = onCountrySelected,
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

        Spacer(modifier = Modifier.height(4.dp))
        TextFieldWithIconAndClear(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            fieldValue = prefix,
            onChanged = onPrefixChanged,
            placeHolderValue = "Enter country name...",
            focusManager = focusManager
        )
        Spacer(modifier = Modifier.height(15.dp))
    }

}

@Composable
fun ShowCountries(
    modifier: Modifier = Modifier,
    countries: List<Country>,
    onCitySelected: (Country) -> Unit,
    selectedCountry: Country?
) {

    LazyColumn(modifier = modifier) {
        itemsIndexed(items = countries) { index, country ->
            Column(
                Modifier
                    .padding(top = 6.dp, bottom = 6.dp)
                    .fillMaxSize()
                    .clickable(
                        onClick = {
                            onCitySelected(country)
                        }
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .fillMaxSize()
                        .background(
                            color = if (country.name == selectedCountry?.name)
                                MaterialTheme.colorScheme.onPrimaryContainer else
                                MaterialTheme.colorScheme.surfaceVariant
                        )

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 15.dp, bottom = 15.dp, start = 5.dp, end = 5.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(.85f),
                            verticalAlignment = Alignment.CenterVertically,
                            //horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = country.name?.common + ", " + country.name?.official + " ${country.capital?.get(0)}",
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactLayoutWithScaffold(
    title: @Composable () -> Unit,
    mainContent: @Composable () -> Unit,
    allowScroll: Boolean = true,
    appScaffoldPaddingValues: PaddingValues = PaddingValues(),
) {

    val sidePadding = 16.dp
    val columnPadding = PaddingValues(
        bottom = appScaffoldPaddingValues.calculateBottomPadding(),
        start = sidePadding,
        end = sidePadding
    )

    var columnModifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(columnPadding)

    if (allowScroll) {
        columnModifier = columnModifier.verticalScroll(rememberScrollState())
    }

    Column(
        modifier = columnModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        title()
        mainContent()
    }
}
