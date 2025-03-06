package com.carce.countrysearch.networkService

import com.carce.countrysearch.model.dto.Country
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryClient {

    @GET(ApiUrls.ALL_COUNTRIES_PATH)
    suspend fun getAllCountries(): List<Country>

    @GET(ApiUrls.COUNTRY_NAME_PATH+"{name}/")
    suspend fun getCountriesByName(@Path("name")name: String): List<Country>
}
