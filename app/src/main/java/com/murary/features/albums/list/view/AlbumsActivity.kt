package com.murary.features.albums.list.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murary.R
import com.murary.features.albums.details.view.AlbumDetailsActivity
import com.murary.features.albums.list.AlbumsPresenter
import com.murary.features.albums.list.AlbumsView
import com.murary.features.albums.model.Album
import com.murary.features.artists.model.Artist
import com.murary.utils.EndlessRecyclerViewScrollListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_albums.*
import javax.inject.Inject

class AlbumsActivity : AppCompatActivity(), AlbumsView,
    AlbumItemClickListener {

    @Inject
    lateinit var presenter: AlbumsPresenter

    private var artist: Artist? = null

    private val columnCount = 2
    private val adapter =
        AlbumsListAdapter(context = this, listener = this)
    private val layoutManager = GridLayoutManager(this, columnCount)

    private val endlessRecyclerViewScrollListener =
        object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.getAlbums(artist)
            }
        }

    override fun onAlbumClick(album: Album) {
        val intent = Intent(this, AlbumDetailsActivity::class.java)
        intent.putExtra(AlbumDetailsActivity.ALBUM, album)

        startActivity(intent)
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        AndroidInjection.inject(this)

        init()
    }

    private fun init() {
        setupActionBar()
        setupRecyclerView()

        artist = intent?.getSerializableExtra(ARTIST) as Artist?

        presenter.attachView(this)

        presenter.getAlbums(artist)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        rvAlbums.adapter = adapter
        rvAlbums.layoutManager = layoutManager

        // used for paging while searching
        rvAlbums.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    companion object {
        const val ARTIST = "ARTIST"
    }
}
