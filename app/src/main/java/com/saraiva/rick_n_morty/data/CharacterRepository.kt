package com.saraiva.rick_n_morty.data

import com.saraiva.rick_n_morty.data.service.RickNMortyService
import com.saraiva.rick_n_morty.domain.model.Character
import com.saraiva.rick_n_morty.domain.model.LocationInfo
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    val rickNMortyService: RickNMortyService
) {
    suspend fun getCharacters(page: Int) =
        rickNMortyService.getCharacters(page).results.map {
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
        }
}