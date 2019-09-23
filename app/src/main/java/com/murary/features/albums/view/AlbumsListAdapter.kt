package com.murary.features.albums.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murary.R
import com.murary.features.albums.model.Album
import kotlinx.android.synthetic.main.listitem_album.view.*

class AlbumsListAdapter(
    private val context: Context,
    private val listener: AlbumItemClickListener
) : RecyclerView.Adapter<AlbumsListAdapter.AlbumViewHolder>() {

    private val albums: ArrayList<Album> = ArrayList()

    fun addAlbums(Albums: List<Album>, clearPrevious: Boolean = false) {
        if (clearPrevious)
            this.albums.clear()

        this.albums.addAll(Albums)
        notifyDataSetChanged()
    }

    fun clear() {
        this.albums.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.listitem_album,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]

        holder.parent.setOnClickListener {
            listener.onAlbumClick(album)
        }

        holder.albumName.text = album.title
        holder.artistName.text = album.artist?.name

        Glide.with(context)
            .load(album.cover_xl)
            .placeholder(R.drawable.img_placeholder)
            .into(holder.image)
    }

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parent: View = view.parentLayout

        val image: ImageView = view.ivAlbumCover
        val albumName: TextView = view.tvAlbumName
        val artistName: TextView = view.tvArtistName
    }
}

interface AlbumItemClickListener {
    fun onAlbumClick(album: Album)
}