package com.saraiva.rick_n_morty.data

import com.saraiva.rick_n_morty.data.datasource.InMemoryDataSource
import com.saraiva.rick_n_morty.data.service.RickNMortyService
import com.saraiva.rick_n_morty.data.model.Character
import com.saraiva.rick_n_morty.data.model.LocationInfo
import com.saraiva.rick_n_morty.data.model.response.PageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    val rickNMortyService: RickNMortyService,
    val inMemory: InMemoryDataSource
) {

    suspend fun getCharacters(page: Int) = flow {
        val pageInfo = rickNMortyService.getCharacters(page)
        saveCharacters(pageInfo.results)
        emit(PageData(pageInfo.results, pageInfo.info.next != null && pageInfo.info.next.isNotEmpty()))
    }

    suspend fun getCharacterById(id: Int): Flow<Character> = withContext(Dispatchers.IO) {
        val deff = async {
            val character = rickNMortyService.getCharacterById(id)
            inMemory.addCharacter(character)
            character
        }
        flowOf( inMemory.getCharacterById(id) ?: deff.await())
    }

    suspend fun getEpisodeList(ids: List<Int>) = flow {
        if (ids.size == 1) {
            emit(listOf(rickNMortyService.getEpisodeById(ids.first())))
        } else {
            emit(rickNMortyService.getEpisodesByIds(ids.joinToString(",")))
        }
    }


    private suspend fun saveCharacters(characters: List<Character>) =
        withContext(Dispatchers.IO) {
            async {
                characters.forEach { inMemory.addCharacter(it) }
            }
        }
}