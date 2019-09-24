package com.murary.features.albums.details

import com.murary.base.BaseView
import com.murary.features.albums.model.Track

interface AlbumDetailsView : BaseView {
    fun showTracks(tracks: List<Track>?)
}