package com.carce.countrysearch.model

import com.google.gson.annotations.SerializedName

data class CountryFlags(
    @SerializedName("png") val png: String? = "",
    @SerializedName("svg") val svg: String? = "",
    @SerializedName("alt") val alt: String? = ""
)
