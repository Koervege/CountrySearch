package com.carce.countrysearch.repository

import com.carce.countrysearch.model.Country
import com.carce.countrysearch.networkService.CountryClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CountryRepository(private val client: CountryClient) {

    fun getCountryList(): Flow<List<Country>> = flow {
        emit(client.getAllCountries())
    }.flowOn(Dispatchers.IO)

    fun getCountriesByName(name: String): Flow<List<Country>> = flow {
        emit(client.getCountriesByName(name))
    }.flowOn(Dispatchers.IO)
}
