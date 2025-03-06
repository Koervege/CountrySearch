package com.carce.countrysearch.repository

import com.carce.countrysearch.model.dto.Country
import com.carce.countrysearch.networkService.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CountryRepository {

    fun getCountryList(): Flow<List<Country>> = flow {
        emit(ApiClient.retrofit.getAllCountries())
    }.flowOn(Dispatchers.IO)

    fun getCountriesByName(name: String): Flow<List<Country>> = flow {
        emit(ApiClient.retrofit.getCountriesByName(name))
    }.flowOn(Dispatchers.IO)
}
