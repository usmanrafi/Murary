package com.murary.features.albums.list

import com.murary.base.BaseView
import com.murary.features.albums.model.Album

interface AlbumsView : BaseView {
    fun showAlbums(albums: List<Album>?)
}