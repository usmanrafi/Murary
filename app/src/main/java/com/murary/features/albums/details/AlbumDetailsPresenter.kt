package com.murary.features.albums.details

import com.murary.base.BasePresenter
import com.murary.network.DeezerGateway
import com.murary.network.NetworkHelper
import javax.inject.Inject

class AlbumDetailsPresenter @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val serviceGateway: DeezerGateway
) : BasePresenter<AlbumDetailsView>() {

}