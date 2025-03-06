package com.carce.countrysearch.networkService

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val gson = GsonBuilder().setLenient().create()

    val retrofit: CountryClient by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ApiUrls.BASE_URL)
            .build().create(CountryClient::class.java)
    }
}