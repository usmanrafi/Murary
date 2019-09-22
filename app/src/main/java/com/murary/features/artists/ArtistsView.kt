package com.murary.features.artists

import com.murary.base.BaseView
import com.murary.features.artists.model.Artist

interface ArtistsView : BaseView {
    fun showArtists(artists: List<Artist>?)
}