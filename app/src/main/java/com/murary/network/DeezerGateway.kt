package com.murary.network

import com.murary.features.albums.model.AlbumDetailsResponseDTO
import com.murary.features.albums.model.AlbumListResponseDTO
import com.murary.features.albums.model.AlbumSearchResponseDTO
import com.murary.features.artists.model.ArtistSearchResponseDTO
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface DeezerGateway {

    @GET("search/artist/")
    fun searchArtists(@Query("q") query: String): Single<Response<ArtistSearchResponseDTO>>

    @GET("search/album")
    fun searchAlbums(@Query("q") query: String): Single<Response<AlbumSearchResponseDTO>>

    @GET("artist/{artistId}/albums")
    fun getAlbums(@Path("artistId") artistId: Int): Single<Response<AlbumListResponseDTO>>

    @GET
    fun executeAlbumSearchUrl(@Url url: String): Single<Response<AlbumSearchResponseDTO>>

    @GET
    fun executeAlbumListUrl(@Url url: String): Single<Response<AlbumListResponseDTO>>

    @GET("album/{albumId}/tracks")
    fun getAlbumTracks(@Path("albumId") albumId: String): Single<Response<AlbumDetailsResponseDTO>>
}