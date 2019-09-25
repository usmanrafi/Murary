package com.murary.features.albums.details.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murary.R
import com.murary.features.albums.model.Track
import kotlinx.android.synthetic.main.listitem_track.view.*

class TracksListAdapter(private val context: Context) :
    RecyclerView.Adapter<TracksListAdapter.TrackViewHolder>() {

    private val tracks: ArrayList<Track> = ArrayList()

    fun addTracks(tracks: List<Track>, clearPrevious: Boolean = false) {
        if (clearPrevious)
            this.tracks.clear()

        this.tracks.addAll(tracks)
        notifyDataSetChanged()
    }

    fun clear() {
        this.tracks.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.listitem_track,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]

        holder.trackNo.text = track.track_position?.toString()
        holder.trackName.text = track.title
        holder.artistName.text = track.artist?.name

    }

    class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trackNo: TextView = view.tvTrackNo
        val trackName: TextView = view.tvTrackName
        val artistName: TextView = view.tvTrackArtists
    }
}