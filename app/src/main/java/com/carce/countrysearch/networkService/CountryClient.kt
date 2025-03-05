package com.carce.countrysearch.networkService

import com.carce.countrysearch.model.dto.Country
import retrofit2.http.GET

interface CountryClient {
    @GET(ApiUrls.ALL_COUNTRIES_URL)
    suspend fun getAllCountries(): List<Country>
}
