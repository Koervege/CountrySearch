package com.carce.countrysearch.model

import com.carce.countrysearch.model.dto.Country

interface CountryAPI {
    fun getAllCountries(): List<Country>

    fun getCountryByName(name: String): Country?

}