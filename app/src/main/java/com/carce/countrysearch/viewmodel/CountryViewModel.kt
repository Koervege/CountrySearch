package com.carce.countrysearch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carce.countrysearch.model.dto.Country
import com.carce.countrysearch.networkService.RequestState
import com.carce.countrysearch.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CountryViewModel: ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    private val _requestState: MutableStateFlow<RequestState> = MutableStateFlow(RequestState.Empty)
    private val _selectedCountry: MutableStateFlow<Country?> = MutableStateFlow(Country())

    val uiState = combine(_searchText, _countries, _requestState, _selectedCountry)
    { searchText, cities, requestState, countryToBeDetailed ->

        SearchUiState(
            searchText,
            cities,
            requestState,
            countryToBeDetailed
        )
    }
    private val countryRepository = CountryRepository()

    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            _requestState.value = RequestState.Loading
            countryRepository.getCountryList()
                .catch { e ->
                    _requestState.value = RequestState.Failure(e)
                    Log.e("Failed to get countries", e.message.orEmpty())
                }.collect { data ->
                    _requestState.value = RequestState.Success(data)
                    _countries.value = data
                }
        }
    }

    fun updateCountryToBeDetailed(country: Country) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedCountry.value = country
        }
    }

    fun updateSearchTextState(searchText: String) {
        _searchText.value = searchText
    }

    private fun clearSearch() {
        _countries.value = emptyList()
        _selectedCountry.value = null
    }

    fun navigateBackToSearch() {
        _selectedCountry.value = null
    }

    fun onCountryNameSearch(prefix: String) {
        _searchText.value = prefix
    }

    fun onCountrySelected(country: Country) {
        _selectedCountry.value = country
    }

}

data class SearchUiState(
    val countryPrefix: String = "",
    val countries: List<Country> = emptyList(),
    val requestState: RequestState = RequestState.Empty,
    val selectedCountry: Country? = null
)
