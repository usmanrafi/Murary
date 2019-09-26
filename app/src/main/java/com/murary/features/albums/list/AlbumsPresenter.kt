package com.murary.features.albums.list

import com.murary.base.BasePresenter
import com.murary.features.albums.model.AlbumListResponseDTO
import com.murary.features.albums.model.AlbumSearchResponseDTO
import com.murary.network.DeezerGateway
import com.murary.network.NetworkHelper
import com.murary.network.ServiceCallback
import javax.inject.Inject

class AlbumsPresenter @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val serviceGateway: DeezerGateway
) : BasePresenter<AlbumsView>() {

    // for searching with paging
    private var cachedResponse: AlbumSearchResponseDTO? = null

    fun searchAlbums(query: String?) {
        query?.let {
            view?.showProgressBar()
            networkHelper.serviceCall(
                if (cachedResponse != null)
                    serviceGateway.executeAlbumSearchUrl(cachedResponse?.next ?: "")
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

    // for paging
    private var cachedListResponse: AlbumListResponseDTO? = null

    fun getAlbums(artistId: Int?) {
        artistId?.let {

            view?.showProgressBar()
            networkHelper.serviceCall(
                if (cachedListResponse != null)
                    serviceGateway.executeAlbumListUrl(cachedListResponse?.next ?: "")
                else
                    serviceGateway.getAlbums(artistId),
                object : ServiceCallback<AlbumListResponseDTO> {
                    override fun onSuccess(response: AlbumListResponseDTO) {
                        cachedListResponse = response

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