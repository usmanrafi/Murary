package com.murary

import com.murary.features.artists.ArtistsPresenter
import com.murary.features.artists.ArtistsView
import com.murary.features.artists.model.ArtistSearchResponseDTO
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
import org.mockito.Mockito.`when`
import retrofit2.Response

class ArtistsPresenterTest {

    @InjectMocks
    private lateinit var presenter: ArtistsPresenter

    @Mock
    private lateinit var networkHelper: NetworkHelper
    @Mock
    private lateinit var serviceGateway: DeezerGateway

    @Mock
    private lateinit var view: ArtistsView

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        presenter.attachView(view)
    }

    @Mock
    private lateinit var searchCall: Single<Response<ArtistSearchResponseDTO>>
    @Captor
    private lateinit var searchCaptor: ArgumentCaptor<ServiceCallback<ArtistSearchResponseDTO>>

    @Mock
    private lateinit var searchResponseDTO: ArtistSearchResponseDTO

    @Test
    fun `searchArtists success test`() {
        `when`(serviceGateway.searchArtists(ArgumentMatchers.anyString())).thenReturn(searchCall)

        presenter.searchArtists("foo")

        verify(networkHelper).serviceCall(
            eq(searchCall),
            capture(searchCaptor)
        )

        searchCaptor.value.onSuccess(searchResponseDTO)

        verify(view).hideProgressBar()
        verify(view).showArtists(searchResponseDTO.data)
    }

    @Test
    fun `searchArtists failure test`() {
        `when`(serviceGateway.searchArtists(ArgumentMatchers.anyString())).thenReturn(searchCall)

        presenter.searchArtists("foo")

        verify(networkHelper).serviceCall(
            eq(searchCall),
            capture(searchCaptor)
        )

        val error = "ERROR"
        searchCaptor.value.onFailure(error)

        verify(view).hideProgressBar()
        verify(view).showToast(error)
    }

    @Test
    fun `empty search query test`(){
        presenter.searchArtists(null)

        verifyNoMoreInteractions(networkHelper)
    }
}