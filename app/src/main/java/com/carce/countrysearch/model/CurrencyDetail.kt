package com.carce.countrysearch.model

import com.google.gson.annotations.SerializedName

data class CurrencyDetail(
    @SerializedName("name") val name: String? = null,
    @SerializedName("symbol") val symbol: String? = null
)
