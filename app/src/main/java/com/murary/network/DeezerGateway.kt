package com.murary.network

import com.murary.features.artists.model.ArtistSearchResponseDTO
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerGateway {

    @GET("search/artist/")
    fun searchArtists(@Query("q") query: String): Single<Response<ArtistSearchResponseDTO>>
}