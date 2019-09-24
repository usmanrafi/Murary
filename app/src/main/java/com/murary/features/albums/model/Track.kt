package com.murary.features.albums.model

import com.murary.features.artists.model.Artist

data class Track(
    val artist: Artist? = null,
    val disk_number: Int? = null,
    val duration: String? = null,
    val explicit_content_cover: Int? = null,
    val explicit_content_lyrics: Int? = null,
    val explicit_lyrics: Boolean? = null,
    val id: String? = null,
    val isrc: String? = null,
    val link: String? = null,
    val preview: String? = null,
    val rank: String? = null,
    val readable: Boolean? = null,
    val title: String? = null,
    val title_short: String? = null,
    val title_version: String? = null,
    val track_position: Int? = null,
    val type: String? = null
)