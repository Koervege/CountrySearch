package com.carce.countrysearch.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val name: CountryNames? = CountryNames(),
    @SerializedName("flags") val flags: CountryFlags? = CountryFlags(),
    @SerializedName("capital") val capital: ArrayList<String>? = arrayListOf(),
    @SerializedName("region") val region: String? = null,
    @SerializedName("subregion") val subregion: String? = null,
    @SerializedName("languages") val languages: Map<String, String>? = emptyMap(),
    @SerializedName("currencies") val currencies: Map<String, CurrencyDetail>? = emptyMap(),
    @SerializedName("population") val population: Int? = null,
    @SerializedName("car") val countryCarInfo: CountryCarInfo? = CountryCarInfo(),
    @SerializedName("coatOfArms") val coatOfArms: CoatOfArms? = CoatOfArms(),
)
