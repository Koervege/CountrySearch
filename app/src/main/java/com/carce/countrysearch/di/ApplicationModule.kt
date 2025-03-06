package com.carce.countrysearch.di

import androidx.compose.ui.tooling.preview.Preview
import com.carce.countrysearch.repository.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideCountryRepository(): CountryRepository {
        return CountryRepository()
    }
}