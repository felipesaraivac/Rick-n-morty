package com.saraiva.rick_n_morty.data.model.response

data class PageData<T>(val data: List<T>, val hasMore: Boolean)
