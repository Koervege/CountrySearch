package com.carce.countrysearch.repository

import com.carce.countrysearch.TestUtils
import com.carce.countrysearch.model.Country
import com.carce.countrysearch.networkService.CountryClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class CountryRepositoryTest {

    private val client: CountryClient = mock(CountryClient::class.java)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val repository = CountryRepository(client)
    private val countryListExample = TestUtils.getCountryListExample()

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
    fun `GIVEN a repository WHEN getAllCountries is called THEN it returns a list of countries`() {
        runBlocking {
            val countries = MutableStateFlow<List<Country>>(emptyList())

            Mockito.`when`(client.getAllCountries()).thenReturn(countryListExample)

            repository.getCountryList().collect{
                countries.value = it
            }
            assert(countries.value == countryListExample)
        }
    }

    @Test
    fun `GIVEN a repository WHEN getCountriesByName is called THEN it returns a list of countries`() {
        runBlocking {
            val countries = MutableStateFlow<List<Country>>(emptyList())

            Mockito.`when`(client.getCountriesByName("de")).thenReturn(countryListExample)

            repository.getCountriesByName("de").collect{
                countries.value = it
            }
            assert(countries.value == countryListExample)
        }
    }
}
