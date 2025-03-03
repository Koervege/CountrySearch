package com.carce.countrysearch.model.dto

import com.google.gson.annotations.SerializedName

data class CountryCarInfo(
    @SerializedName("side") val drivingSide: String? = ""
)
