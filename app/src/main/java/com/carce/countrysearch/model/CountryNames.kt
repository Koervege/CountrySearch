package com.carce.countrysearch.model

import com.google.gson.annotations.SerializedName

data class CountryNames(
    @SerializedName("common") val common: String? = "",
    @SerializedName("official") val official: String? = "",
)
