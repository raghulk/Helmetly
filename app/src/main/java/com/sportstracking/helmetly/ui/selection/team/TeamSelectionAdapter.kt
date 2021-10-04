package com.sportstracking.helmetly.ui.selection.team

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.data.TeamArray.Team
import com.sportstracking.helmetly.ui.selection.FavoriteSelectionViewModel
import com.sportstracking.helmetly.ui.selection.sport.SportSelectionFragmentDirections
import com.sportstracking.helmetly.utility.Utility
import java.util.*

class TeamSelectionAdapter(
    private val favoriteSelectionViewModel: FavoriteSelectionViewModel,
    private val teams: MutableList<Team> = mutableListOf()
) : RecyclerView.Adapter<TeamSelectionAdapter.TeamViewHolder>(), Filterable {

    private lateinit var context: Context
    private val teamDataComplete: MutableList<Team> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.team_list_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.apply {
            teamName.text = teams[position].strTeam
            setImage(teams[position].strTeamBadge.toString(), teamLogo)
            itemView.setOnClickListener {
                val subscribeToChanges: SubscribeToChanges = favoriteSelectionViewModel
                subscribeToChanges.add(teams[position])
            }
            val screenWidth = Utility().getScreenSize(context)[0]
            container.minWidth = (screenWidth.toInt() / 3) - 35
            infoButton.setOnClickListener {
                val directions =
                    TeamSelectionFragmentDirections.actionNavigationTeamSelectionToTeamInfoFragment(
                        teams[position].idTeam
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
        return teams.size
    }

    fun setTeams(teams: List<Team>) {
        teamDataComplete.clear()
        this.teams.clear()
        teamDataComplete.addAll(teams)
        this.teams.addAll(teams)
    }


    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: ConstraintLayout = view.findViewById(R.id.list_item_container)
        val teamName: TextView = view.findViewById(R.id.team_name)
        val teamLogo: ImageView = view.findViewById(R.id.team_logo)
        val infoButton: ImageView = view.findViewById(R.id.info_button)
    }

    override fun getFilter(): Filter {
        val filter: Filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredTeamsData: ArrayList<Team> = ArrayList<Team>()
                if (constraint.isEmpty()) {
                    filteredTeamsData.addAll(teamDataComplete)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (team in teamDataComplete) {
                        if (team.strTeam.toLowerCase()
                                .contains(filterPattern)
                        ) {
                            filteredTeamsData.add(team)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredTeamsData
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                teams.clear()
                teams.addAll(results.values as ArrayList<Team>)
                notifyDataSetChanged()
            }
        }
        return filter
    }

    interface SubscribeToChanges {
        fun add(team: Team)
        fun remove(team: Team)
    }
}