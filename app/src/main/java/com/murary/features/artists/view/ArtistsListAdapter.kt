package com.murary.features.artists.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murary.R
import com.murary.features.artists.model.Artist
import kotlinx.android.synthetic.main.listitem_artist.view.*

class ArtistsListAdapter(
    private val context: Context,
    private val listener: ArtistItemClickListener
) : RecyclerView.Adapter<ArtistsListAdapter.ArtistViewHolder>() {

    private val artists: ArrayList<Artist> = ArrayList()

    fun addArtists(artists: List<Artist>, clearPrevious: Boolean = false) {
        if(clearPrevious)
            this.artists.clear()

        this.artists.addAll(artists)
        notifyDataSetChanged()
    }

    fun clear() {
        this.artists.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return ArtistViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.listitem_artist,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artists[position]

        holder.parent.setOnClickListener {
            listener.onArtistClick(artist)
        }

        holder.name.text = artist.name

        Glide.with(context)
            .load(artist.picture_big)
            .into(holder.image)
    }

    class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parent: View = view.parentLayout

        val image: ImageView = view.ivArtist
        val name: TextView = view.tvArtistName
    }
}

interface ArtistItemClickListener {
    fun onArtistClick(artist: Artist)
}