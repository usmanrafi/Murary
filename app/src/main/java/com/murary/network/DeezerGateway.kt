package com.murary.network

import com.murary.features.albums.model.AlbumDetailsResponseDTO
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

    @GET
    fun executeAlbumUrl(@Url url: String): Single<Response<AlbumSearchResponseDTO>>

    @GET("album/{albumId}/tracks")
    fun getAlbumDetails(@Path("albumId") albumId: Int): Single<Response<AlbumDetailsResponseDTO>>
}