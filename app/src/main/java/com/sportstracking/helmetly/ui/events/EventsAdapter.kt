package com.sportstracking.helmetly.ui.events

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.data.Result.Event
import com.sportstracking.helmetly.utility.Utility
import java.text.SimpleDateFormat

class EventsAdapter(
    private val events: MutableList<Event> = mutableListOf()
) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.event_list_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.apply {
            eventName.text = events[position].strEvent
            setDate(events[position].dateEvent, eventDate)
            setImage(events[position].strThumb, eventImage, events[position].strSport)
            setEventLeague(events[position].strLeague, eventLeague)
            setScoreDetails(events[position].intHomeScore, events[position].intAwayScore, scoreDetails)
        }
    }


    private fun setImage(imgUrl: String?, imageView: ImageView, sport: String) {
        var imageURL = imgUrl
        val screenWidth = Integer.parseInt(Utility().getScreenSize(context)[0])
        if (imgUrl.isNullOrEmpty()){
            imageURL = "https://source.unsplash.com/${screenWidth}x600/?$sport&${Math.random()}"
        }
        Glide.with(context)
            .asDrawable()
            .load(imageURL)
            .apply(RequestOptions().transform(RoundedCorners(8)).override(screenWidth, 240))
            .into(imageView)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setDate(date: String?, dateView: TextView) {
        if (date != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("dd-MMM-yyyy")
            val dateText = inputFormat.parse(date)
            dateText?.let { dateView.text = outputFormat.format(dateText) }
        } else {
            dateView.visibility = View.GONE
        }
    }

    private fun setEventLeague(league: String?, leagueView: TextView) {
        if(league != null) {
            leagueView.text = league
        } else{
            leagueView.visibility = View.GONE
        }
    }

    private fun setScoreDetails(homeScore: String?, awayScore: String?, scoreView: TextView) {
        if(homeScore!= null && awayScore != null) {
            scoreView.text = "$homeScore - $awayScore"
        }
        else {
            scoreView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setEvents(events: List<Event>) {
        this.events.clear()
        this.events.addAll(events)
    }


    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: TextView = view.findViewById(R.id.event_name)
        val eventDate: TextView = view.findViewById(R.id.event_date)
        val eventImage: ImageView = view.findViewById(R.id.event_image)
        val eventLeague: TextView = view.findViewById(R.id.event_league)
        val scoreDetails: TextView = view.findViewById(R.id.score_details)
    }
}