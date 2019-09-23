package com.murary.features.albums

import com.murary.base.BasePresenter
import com.murary.features.albums.model.AlbumSearchResponseDTO
import com.murary.network.DeezerGateway
import com.murary.network.NetworkHelper
import com.murary.network.ServiceCallback
import javax.inject.Inject

class AlbumsPresenter @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val serviceGateway: DeezerGateway
) : BasePresenter<AlbumsView>() {

    private var cachedResponse: AlbumSearchResponseDTO? = null

    fun searchAlbums(query: String?) {
        query?.let {
            view?.showProgressBar()
            networkHelper.serviceCall(
                if (cachedResponse != null)
                    serviceGateway.executeAlbumUrl(cachedResponse?.next ?: "")
                else
                    serviceGateway.searchAlbums(query),

                object : ServiceCallback<AlbumSearchResponseDTO> {
                    override fun onSuccess(response: AlbumSearchResponseDTO) {
                        cachedResponse = response

                        view?.showAlbums(response.data)
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