package com.sportstracking.helmetly.ui.selection.sport

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.data.SportsArray.Sport

class SportSelectionAdapter(private val sports: MutableList<Sport> = mutableListOf()) :
    RecyclerView.Adapter<SportSelectionAdapter.SportViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.sport_list_item, parent, false)
        return SportViewHolder(view)
    }

    override fun onBindViewHolder(holder: SportViewHolder, position: Int) {
        holder.apply {
            sportName.text = sports[position].strSport
            setImage(sports[position].strSportThumb, sportImage)
            itemView.setOnClickListener {
                val directions =
                    SportSelectionFragmentDirections.actionNavigationSportSelectionToNavigationCountrySelection(
                        sports[position].strSport
                    )
                it.findNavController().navigate(directions)
            }
        }
    }

    private fun setImage(imgUrl: String, imageView: ImageView) {
        Glide.with(context)
            .asDrawable()
            .load(imgUrl)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    imageView.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    override fun getItemCount(): Int {
        return sports.size
    }

    fun setSports(sports: List<Sport>) {
        this.sports.addAll(sports)
    }

    class SportViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sportImage: ImageView = view.findViewById(R.id.sport_image)
        val sportName: TextView = view.findViewById(R.id.sport_text)
    }
}