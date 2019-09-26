package com.murary

import com.murary.features.albums.list.AlbumsPresenter
import com.murary.features.albums.list.AlbumsView
import com.murary.features.albums.model.AlbumSearchResponseDTO
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

class AlbumsPresenterTest {

    @InjectMocks
    private lateinit var presenter: AlbumsPresenter

    @Mock
    private lateinit var networkHelper: NetworkHelper
    @Mock
    private lateinit var serviceGateway: DeezerGateway

    @Mock
    private lateinit var view: AlbumsView

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        presenter.attachView(view)
    }

    @Mock
    private lateinit var searchCall: Single<Response<AlbumSearchResponseDTO>>
    @Captor
    private lateinit var searchCaptor: ArgumentCaptor<ServiceCallback<AlbumSearchResponseDTO>>

    @Mock
    private lateinit var searchResponseDTO: AlbumSearchResponseDTO

    @Test
    fun `searchAlbums success test`() {
        Mockito.`when`(serviceGateway.searchAlbums(ArgumentMatchers.anyString())).thenReturn(searchCall)

        presenter.searchAlbums("foo")

        verify(networkHelper).serviceCall(
            eq(searchCall),
            capture(searchCaptor)
        )

        searchCaptor.value.onSuccess(searchResponseDTO)

        verify(view).hideProgressBar()
        verify(view).showAlbums(searchResponseDTO.data)
    }

    @Test
    fun `searchAlbums paged success test`() {
        Mockito.`when`(serviceGateway.searchAlbums(ArgumentMatchers.anyString())).thenReturn(searchCall)

        presenter.searchAlbums("foo")

        verify(networkHelper).serviceCall(
            eq(searchCall),
            capture(searchCaptor)
        )

        searchCaptor.value.onSuccess(searchResponseDTO)

        verify(view).hideProgressBar()
        verify(view).showAlbums(searchResponseDTO.data)

        presenter.searchAlbums("foo")

        verify(serviceGateway).executeAlbumSearchUrl(searchResponseDTO.next ?: "")
    }

    @Test
    fun `searchAlbums failure test`() {
        Mockito.`when`(serviceGateway.searchAlbums(ArgumentMatchers.anyString())).thenReturn(searchCall)

        presenter.searchAlbums("foo")

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
        presenter.searchAlbums(null)

        verifyNoMoreInteractions(networkHelper)
    }
}