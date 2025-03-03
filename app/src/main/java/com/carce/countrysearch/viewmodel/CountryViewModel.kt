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
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {

    private val requestState: MutableStateFlow<RequestState> = MutableStateFlow(RequestState.Empty)
    private val countryToBeDetailed: MutableStateFlow<Country> = MutableStateFlow(Country())
    private val countryRepository = CountryRepository()

    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            requestState.value = RequestState.Loading
            countryRepository.getCountryList()
                .catch { e ->
                    requestState.value = RequestState.Failure(e)
                    Log.e("Failed to get countries", e.message.orEmpty())
                }.collect { data ->
                    requestState.value = RequestState.Success(data)
                }
        }
    }

    fun updateCountryToBeDetailed(country: Country) {
        viewModelScope.launch(Dispatchers.IO) {
            countryToBeDetailed.value = country
        }
    }
}
