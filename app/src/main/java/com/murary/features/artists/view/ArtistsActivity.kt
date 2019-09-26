package com.murary.features.artists.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.murary.R
import com.murary.features.albums.list.view.AlbumsActivity
import com.murary.features.artists.ArtistsPresenter
import com.murary.features.artists.ArtistsView
import com.murary.features.artists.model.Artist
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_artists.*
import javax.inject.Inject


class ArtistsActivity : AppCompatActivity(), ArtistsView, ArtistItemClickListener {

    @Inject
    lateinit var presenter: ArtistsPresenter

    private val adapter = ArtistsListAdapter(context = this, listener = this)

    override fun showArtists(artists: List<Artist>?) {
        artists?.let {
            this.adapter.addArtists(it, clearPrevious = true)
        } ?: showToast("Error")
    }

    override fun onArtistClick(artist: Artist) {
        val intent = Intent(this, AlbumsActivity::class.java)
        intent.putExtra(AlbumsActivity.ARTIST, artist)
        startActivity(intent)
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
        setContentView(R.layout.activity_artists)

        AndroidInjection.inject(this)

        init()
    }

    private fun init() {
        presenter.attachView(this)

        rvArtists.adapter = adapter
        setupSearchView()
    }

    private fun setupSearchView() {
        svArtists.findViewById<EditText>(androidx.appcompat.R.id.search_src_text).also {
            it.setTextColor(ContextCompat.getColor(this, R.color.primaryDarkColor))
            it.setHintTextColor(ContextCompat.getColor(this, R.color.primaryColor))
        }

        svArtists.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.searchArtists(query?.trim())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText?.trim()
                query?.let {
                    if (query.length >= 3)
                        presenter.searchArtists(query)
                    else if (query.isBlank())
                        adapter.clear()

                } ?: adapter.clear()
                return false
            }
        })
    }

}
