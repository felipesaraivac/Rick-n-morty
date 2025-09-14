package com.saraiva.rick_n_morty.domain.usecase

import com.saraiva.rick_n_morty.data.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
)  {
}