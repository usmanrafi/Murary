package com.murary.features.albums.details.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.murary.R
import com.murary.features.albums.details.AlbumDetailsPresenter
import com.murary.features.albums.details.AlbumDetailsView
import com.murary.features.albums.model.Album
import com.murary.features.albums.model.Track
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_album_details.*
import javax.inject.Inject


class AlbumDetailsActivity : AppCompatActivity(), AlbumDetailsView {

    private var album: Album? = null

    @Inject
    lateinit var presenter: AlbumDetailsPresenter

    private val adapter = TracksListAdapter(context = this)

    override fun showTracks(tracks: List<Track>?) {
        tracks?.let {
            adapter.addTracks(tracks)
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
        setContentView(R.layout.activity_album_details)

        AndroidInjection.inject(this)

        init()
    }

    private fun init() {
        album = intent?.getSerializableExtra(ALBUM) as Album?

        initViews()

        presenter.attachView(this)
        presenter.getAlbumTracks(album?.id)
    }

    private fun initViews() {
        ivBack.setOnClickListener {
            finish()
        }

        Glide.with(this)
            .load(album?.cover_xl)
            .placeholder(R.drawable.img_placeholder)
            .into(ivAlbumCover)

        tvAlbumName.text = album?.title
        tvArtistName.text = album?.artist?.name

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvTracks.adapter = adapter
    }

    companion object {
        const val ALBUM = "ALBUM"
    }
}
