package com.murary.features.artists

import com.murary.base.BasePresenter
import com.murary.features.artists.model.ArtistSearchResponseDTO
import com.murary.network.DeezerGateway
import com.murary.network.NetworkHelper
import com.murary.network.ServiceCallback
import javax.inject.Inject

class ArtistsPresenter @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val serviceGateway: DeezerGateway
) : BasePresenter<ArtistsView>() {

    fun searchArtists(query: String?) {
        query?.let {
            view?.showProgressBar()

            networkHelper.serviceCall(
                serviceGateway.searchArtists(query),
                object : ServiceCallback<ArtistSearchResponseDTO> {
                    override fun onSuccess(response: ArtistSearchResponseDTO) {
                        view?.showArtists(response.data)
                        view?.hideProgressBar()
                    }

                    override fun onFailure(error: String) {
                        view?.hideProgressBar()
                        view?.showToast(error)
                    }
                })
        }
    }
}