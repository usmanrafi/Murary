package com.murary

import com.murary.features.albums.details.AlbumDetailsPresenter
import com.murary.features.albums.details.AlbumDetailsView
import com.murary.features.albums.model.AlbumDetailsResponseDTO
import com.murary.network.DeezerGateway
import com.murary.network.NetworkHelper
import com.murary.network.ServiceCallback
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.*
import retrofit2.Response

class AlbumDetailsPresenterTest {

    @InjectMocks
    private lateinit var presenter: AlbumDetailsPresenter

    @Mock
    private lateinit var networkHelper: NetworkHelper
    @Mock
    private lateinit var serviceGateway: DeezerGateway

    @Mock
    private lateinit var view: AlbumDetailsView

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        presenter.attachView(view)
    }

    @Mock
    private lateinit var getCall: Single<Response<AlbumDetailsResponseDTO>>
    @Captor
    private lateinit var getCaptor: ArgumentCaptor<ServiceCallback<AlbumDetailsResponseDTO>>

    @Mock
    private lateinit var albumDetailsResponseDTO: AlbumDetailsResponseDTO

    @Test
    fun `getAlbums success test`() {
        Mockito.`when`(serviceGateway.getAlbumTracks(ArgumentMatchers.anyString())).thenReturn(getCall)

        presenter.getAlbumTracks("1")

        verify(networkHelper).serviceCall(
            eq(getCall),
            capture(getCaptor)
        )

        getCaptor.value.onSuccess(albumDetailsResponseDTO)

        verify(view).hideProgressBar()
        verify(view).showTracks(albumDetailsResponseDTO.data)
    }

    @Test
    fun `getAlbums failure test`() {
        Mockito.`when`(serviceGateway.getAlbumTracks(ArgumentMatchers.anyString())).thenReturn(getCall)

        presenter.getAlbumTracks("1")

        verify(networkHelper).serviceCall(
            eq(getCall),
            capture(getCaptor)
        )

        val error = "ERROR"
        getCaptor.value.onFailure(error)

        verify(view).hideProgressBar()
        verify(view).showToast(error)
    }

    @Test
    fun `null artist id test`() {
        presenter.getAlbumTracks(null)

        verifyNoMoreInteractions(networkHelper)
    }


}