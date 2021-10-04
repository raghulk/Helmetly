package com.sportstracking.helmetly.ui.selection

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.data.TeamArray.Team
import com.sportstracking.helmetly.ui.selection.team.TeamSelectionAdapter
import com.sportstracking.helmetly.utility.Utility

class SelectedTeamsAdapter(
    private val favoriteSelectionViewModel: FavoriteSelectionViewModel,
    private val selectedTeams: MutableList<Team> = mutableListOf()
) : RecyclerView.Adapter<SelectedTeamsAdapter.TeamViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.selected_team_list_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.apply {
            teamName.text = selectedTeams[position].strTeam
            setImage(selectedTeams[position].strTeamBadge.toString(), teamLogo)
            removeButton.setOnClickListener{
                val subscribeToChanges: TeamSelectionAdapter.SubscribeToChanges = favoriteSelectionViewModel
                subscribeToChanges.remove(selectedTeams[position])
            }
            val screenWidth = Utility().getScreenSize(context)[0]
            container.minWidth = (screenWidth.toInt() / 3) - 35
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

                override fun onLoadCleared(placeholder: Drawable?) { }
            })
    }

    override fun getItemCount(): Int {
        return selectedTeams.size
    }

    fun setTeams(teams: List<Team>) {
        selectedTeams.clear()
        selectedTeams.addAll(teams)
    }


    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: ConstraintLayout = view.findViewById(R.id.list_item_container)
        val teamName: TextView = view.findViewById(R.id.team_name)
        val teamLogo: ImageView = view.findViewById(R.id.team_logo)
        val removeButton: ImageView = view.findViewById(R.id.remove_button)
    }
}