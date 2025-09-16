package com.saraiva.rick_n_morty.domain.usecase

import com.saraiva.rick_n_morty.data.CharacterRepository
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(page: Int) = flowOf(characterRepository.getCharacters(page))
}