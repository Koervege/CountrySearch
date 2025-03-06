package com.carce.countrysearch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carce.countrysearch.model.dto.Country
import com.carce.countrysearch.networkService.RequestState
import com.carce.countrysearch.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CountryViewModel @Inject constructor(
    private val countryRepository: CountryRepository
): ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val _allCountries = MutableStateFlow<List<Country>>(emptyList())
    private val _countriesToBeShown = MutableStateFlow<List<Country>>(emptyList())
    private val _requestState: MutableStateFlow<RequestState> = MutableStateFlow(RequestState.Empty)
    private val _selectedCountry: MutableStateFlow<Country?> = MutableStateFlow(null)

    val uiState = combine(_searchText, _countriesToBeShown, _requestState, _selectedCountry)
    { searchText, countries, requestState, countryToBeDetailed ->

        SearchUiState(
            searchText,
            countries,
            requestState,
            countryToBeDetailed
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchUiState()
    )


    init {
        _searchText
            .debounce(300) // gets the latest; no need for delays!
            .filter { cityPrefix -> (cityPrefix.length > 1) } // make sure there's enough initial text to search for
            .distinctUntilChanged() // to avoid duplicate network calls
            .flowOn(Dispatchers.IO) // Changes the context where this flow is executed to Dispatchers.IO
            .onEach { cityPrefix -> // just gets the prefix: 'ph', 'pho', 'phoe'
                getCountriesByPrefix(cityPrefix)
            }
            .launchIn(viewModelScope)
    }

    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            _requestState.value = RequestState.Loading
            countryRepository.getCountryList()
                .catch { e ->
                    _requestState.value = RequestState.Failure(e)
                    Log.e("Failed to get countries", e.message.orEmpty())
                }.collect { data ->
                    _requestState.value = RequestState.Success
                    _allCountries.value = data
                    _countriesToBeShown.value = data
                }
        }
    }

    fun updateCountryToBeDetailed(country: Country) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedCountry.value = country
        }
    }

    private fun clearSearch() {
        if (_allCountries.value.isEmpty()) getCountries()
        _countriesToBeShown.value = _allCountries.value
        _selectedCountry.value = null
    }

    fun navigateBackToSearch() {
        _selectedCountry.value = null
    }

    fun onCountryNameSearch(prefix: String) {
        if (prefix.length <= 1) clearSearch()
        _searchText.value = prefix
    }

    fun onCountrySelected(country: Country?) {
        _selectedCountry.value = country
    }

    private fun getCountriesByPrefix(prefix: String) {
        viewModelScope.launch(Dispatchers.IO) {
            countryRepository.getCountriesByName(prefix)
                .catch { e ->
                    Log.e("Failed to get countries", e.message.orEmpty())
                }.collect { data ->
                    _requestState.value = RequestState.Success
                    _countriesToBeShown.value = data
                }
        }
    }

}

data class SearchUiState(
    val countryPrefix: String = "",
    val countries: List<Country> = emptyList(),
    val requestState: RequestState = RequestState.Empty,
    val selectedCountry: Country? = null
)
