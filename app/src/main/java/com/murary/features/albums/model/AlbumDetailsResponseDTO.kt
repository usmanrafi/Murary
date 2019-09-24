package com.murary.features.albums.model

data class AlbumDetailsResponseDTO(
    val data: List<Track>? = null,
    val total: Int? = null
)