package com.sportstracking.helmetly.ui.selection.team

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sportstracking.helmetly.data.TeamArray.Team
import com.sportstracking.helmetly.databinding.FragmentTeamInfoBinding
import com.sportstracking.helmetly.ui.selection.FavoriteSelectionViewModel

class TeamInfoFragment : Fragment() {
    private lateinit var favoriteSelectionViewModel: FavoriteSelectionViewModel
    private lateinit var selectedTeamId: String
    private lateinit var selectedTeam: Team
    private var _binding: FragmentTeamInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context as AppCompatActivity).supportActionBar?.show()
        favoriteSelectionViewModel =
            ViewModelProvider(requireActivity()).get(FavoriteSelectionViewModel::class.java)
        selectedTeamId = arguments?.getString(TEAM_ID).toString()
        selectedTeam = favoriteSelectionViewModel.teamsData.value?.teams?.filter {
            it.idTeam == selectedTeamId
        }?.get(0)!!
        (context as AppCompatActivity).supportActionBar?.title = selectedTeam.strTeam
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamInfoBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        selectedTeam.let {
            binding.apply {
                sportPlayed.text = it.strSport
                country.text = it.strCountry
                setStadium(it.strStadium, stadium)
                setLeague(it.strLeague, league)
                gender.text = it.strGender
                setFormedYear(it.intFormedYear, formedYear)
                setDescription(it.strDescriptionEN, description)
                setImage(it.strTeamFanArt1.toString(), fanArtImage)
            }
        }
    }

    private fun setDescription(description: String?, descriptionView: TextView) {
        if (!description.isNullOrBlank()) {
            descriptionView.text = description
        } else {
            binding.descriptionContainer.visibility = View.GONE
        }
    }

    private fun setLeague(league: String?, leagueView: TextView) {
        if (!league.isNullOrBlank()) {
            leagueView.text = league
        } else {
            binding.leagueContainer.visibility = View.GONE
        }
    }

    private fun setStadium(stadium: String?, stadiumView: TextView) {
        if (!stadium.isNullOrBlank()) {
            stadiumView.text = stadium
        } else {
            binding.stadiumContainer.visibility = View.GONE
        }
    }

    private fun setFormedYear(formedYear: String?, formedYearView: TextView) {
        if (formedYear != null && formedYear != "0") {
            formedYearView.text = formedYear
        } else {
            binding.formedYearContainer.visibility = View.GONE
        }
    }

    private fun setImage(imgUrl: String, imageView: ImageView) {
        if (imgUrl !== "null") {
            Glide.with(requireContext())
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
        } else {
            imageView.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TEAM_ID = "teamId"
    }
}