package com.saraiva.rick_n_morty.di

import com.saraiva.rick_n_morty.data.CharacterRepository
import com.saraiva.rick_n_morty.domain.usecase.GetCharactersUseCase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Singleton
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetCharactersUseCase(repository: CharacterRepository) =
        GetCharactersUseCase(repository)
}