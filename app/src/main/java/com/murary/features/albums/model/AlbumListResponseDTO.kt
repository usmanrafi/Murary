package com.murary.features.albums.model

data class AlbumListResponseDTO(
    val data: List<Album>? = null,
    val next: String? = null,
    val prev: String? = null,
    val total: Int? = null
)