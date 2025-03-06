package com.carce.countrysearch.di

import androidx.compose.ui.tooling.preview.Preview
import com.carce.countrysearch.networkService.ApiUrls
import com.carce.countrysearch.networkService.CountryClient
import com.carce.countrysearch.repository.CountryRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideCountryRepository(client: CountryClient): CountryRepository {
        return CountryRepository(client)
    }

    @Provides
    fun provideCountryClientImplementation(): CountryClient {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ApiUrls.BASE_URL)
            .build().create(CountryClient::class.java)
    }
}
