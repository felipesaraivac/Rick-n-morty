package com.saraiva.rick_n_morty.data.service

import com.saraiva.rick_n_morty.data.model.CharacterModel
import com.saraiva.rick_n_morty.data.model.response.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface RickNMortyService {

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
    ): PaginatedResponse<CharacterModel>
}