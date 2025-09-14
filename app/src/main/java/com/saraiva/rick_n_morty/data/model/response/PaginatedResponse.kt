package com.saraiva.rick_n_morty.data.model.response

class PaginatedResponse<T>(
    val info: PageInfo,
    val results: List<T>
)

class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)