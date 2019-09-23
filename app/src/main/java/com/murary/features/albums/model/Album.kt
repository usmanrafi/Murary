package com.murary.features.albums.model

import com.murary.features.artists.model.Artist

data class Album(
    val artist: Artist? = null,
    val cover: String? = null,
    val cover_big: String? = null,
    val cover_medium: String? = null,
    val cover_small: String? = null,
    val cover_xl: String? = null,
    val explicit_lyrics: Boolean? = null,
    val genre_id: Int? = null,
    val id: String? = null,
    val link: String? = null,
    val nb_tracks: Int? = null,
    val record_type: String? = null,
    val title: String? = null,
    val tracklist: String? = null,
    val type: String? = null
)