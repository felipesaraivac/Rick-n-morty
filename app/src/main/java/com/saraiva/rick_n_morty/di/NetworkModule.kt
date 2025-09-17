package com.saraiva.rick_n_morty.di

import com.saraiva.rick_n_morty.data.CharacterRepository
import com.saraiva.rick_n_morty.data.core.Constants
import com.saraiva.rick_n_morty.data.datasource.InMemoryDataSource
import com.saraiva.rick_n_morty.data.service.RickNMortyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideRickNMortyApi(retrofit: Retrofit): RickNMortyService =
        retrofit.create(RickNMortyService::class.java)

    @Provides
    @Singleton
    fun provideInMemoryDataSource(): InMemoryDataSource = InMemoryDataSource()

    @Provides
    @Singleton
    fun provideCharacterRepository(
        rickNMortyService: RickNMortyService,
        inMemoryDataSource: InMemoryDataSource
    ): CharacterRepository =
        CharacterRepository(rickNMortyService, inMemoryDataSource)
}