package com.saraiva.rick_n_morty.data.service

import com.saraiva.rick_n_morty.data.model.Character
import com.saraiva.rick_n_morty.data.model.Episode
import com.saraiva.rick_n_morty.data.model.response.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickNMortyService {

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
    ): PaginatedResponse<Character>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int,
    ): Character

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int,
    ): Episode

    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(
        @Path("ids") ids: String,
    ): List<Episode>
}