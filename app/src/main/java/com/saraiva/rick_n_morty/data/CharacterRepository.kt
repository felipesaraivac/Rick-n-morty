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
        emit(PageData(pageInfo.results.map {
            Character(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                type = it.type,
                gender = it.gender,
                origin = LocationInfo(
                    name = it.origin.name,
                    url = it.origin.url,
                ),
                location = LocationInfo(
                    name = it.location.name,
                    url = it.location.url,
                ),
                image = it.image,
                episode = it.episode,
                url = it.url,
                created = it.status,
            )
        }, !pageInfo.info.next.isNullOrEmpty()))
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
        emit(rickNMortyService.getEpisodesByIds(ids.joinToString(",")))
    }


    private suspend fun saveCharacters(characters: List<Character>) =
        withContext(Dispatchers.IO) {
            async {
                characters.forEach { inMemory.addCharacter(it) }
            }
        }
}