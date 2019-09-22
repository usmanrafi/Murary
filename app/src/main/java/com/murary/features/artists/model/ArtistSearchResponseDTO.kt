package com.murary.features.artists.model

data class ArtistSearchResponseDTO(
    val data: List<Artist>? = null,
    val next: String? = null,
    val prev: String? = null,
    val total: Int? = null
)