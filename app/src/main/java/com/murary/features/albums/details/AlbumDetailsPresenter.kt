package com.murary.features.albums.details

import com.murary.base.BasePresenter
import com.murary.features.albums.model.AlbumDetailsResponseDTO
import com.murary.network.DeezerGateway
import com.murary.network.NetworkHelper
import com.murary.network.ServiceCallback
import javax.inject.Inject

class AlbumDetailsPresenter @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val serviceGateway: DeezerGateway
) : BasePresenter<AlbumDetailsView>() {

    fun getAlbumTracks(albumId: String?) {
        albumId?.let {

            view?.showProgressBar()

            networkHelper.serviceCall(
                serviceGateway.getAlbumTracks(albumId),
                object : ServiceCallback<AlbumDetailsResponseDTO> {
                    override fun onSuccess(response: AlbumDetailsResponseDTO) {
                        view?.showTracks(response.data)
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