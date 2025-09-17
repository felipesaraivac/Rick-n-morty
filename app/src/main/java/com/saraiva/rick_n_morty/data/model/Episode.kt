package com.saraiva.rick_n_morty.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
)
