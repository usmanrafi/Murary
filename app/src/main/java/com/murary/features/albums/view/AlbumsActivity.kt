package com.murary.features.albums.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murary.R
import com.murary.features.albums.AlbumsPresenter
import com.murary.features.albums.AlbumsView
import com.murary.features.albums.model.Album
import com.murary.utils.EndlessRecyclerViewScrollListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_albums.*
import javax.inject.Inject

class AlbumsActivity : AppCompatActivity(), AlbumsView, AlbumItemClickListener {

    @Inject
    lateinit var presenter: AlbumsPresenter

    private var artistName: String? = null

    private val columnCount = 2
    private val adapter = AlbumsListAdapter(context = this, listener = this)
    private val layoutManager = GridLayoutManager(this, columnCount)

    private val endlessRecyclerViewScrollListener =
        object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.searchAlbums(artistName)
            }
        }

    override fun onAlbumClick(album: Album) {
        showToast(album.title ?: "Error")
    }

    override fun showAlbums(albums: List<Album>?) {
        albums?.let {
            adapter.addAlbums(albums)
        }
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showToast(toastMessage: String) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        AndroidInjection.inject(this)

        init()
    }

    private fun init() {
        artistName = intent?.getStringExtra(ARTIST_NAME)

        presenter.attachView(this)

        rvAlbums.adapter = adapter
        rvAlbums.layoutManager = layoutManager
        rvAlbums.addOnScrollListener(endlessRecyclerViewScrollListener)

        presenter.searchAlbums(artistName)
    }

    companion object {
        const val ARTIST_NAME = "ARTIST_NAME"
    }
}
