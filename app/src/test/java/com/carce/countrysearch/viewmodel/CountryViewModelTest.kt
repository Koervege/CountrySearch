package com.carce.countrysearch.viewmodel

import androidx.lifecycle.viewModelScope
import com.carce.countrysearch.TestUtils
import com.carce.countrysearch.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {

    private val repository = mock(CountryRepository::class.java)
    private val viewModel = CountryViewModel(repository);
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val countryList = TestUtils.getCountryListExample()


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `GIVEN a CountryViewModel WHEN getCountries is called THEN verify repository is called`() {
        viewModel.getCountries()
        verify(repository).getCountryList()
    }

    @Test
    fun `GIVEN a CountryViewModel WHEN onCountryNameSearch is called with a string of length 2 THEN verify repository is called`() {
        runBlocking {
            viewModel.onCountryNameSearch("de")
            delay(601)
            verify(repository).getCountriesByName("de")
        }
    }

    @Test
    fun `GIVEN a CountryViewModel WHEN onCountryNameSearch is called with a string shorter than 2 THEN verify repository is not called`() {
        runBlocking {
            viewModel.onCountryNameSearch("d")
            delay(601)
            verify(repository, Mockito.never()).getCountriesByName("d")
        }
    }

    @Test
    fun `GIVEN a CountryViewModel WHEN flow members are updated THEN verify uiState is updated`() {
        runBlocking {
            viewModel.viewModelScope.launch {
                Mockito.`when`(repository.getCountryList()).thenReturn(flow { countryList })
                assert(viewModel.uiState.value == SearchUiState())
                viewModel.onCountryNameSearch("")
                viewModel.onCountrySelected(countryList[0])
                delay(600)
                assert(viewModel.uiState.value.selectedCountry != null)
                assert(viewModel.uiState.value.countries == countryList)
            }
        }
    }
}
