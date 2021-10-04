package com.sportstracking.helmetly.ui.selection.team

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sportstracking.helmetly.HomeActivity
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.data.TeamArray.Team
import com.sportstracking.helmetly.databinding.FragmentTeamSelectionBinding
import com.sportstracking.helmetly.ui.selection.FavoriteSelectionViewModel
import com.sportstracking.helmetly.utility.SharedPrefHelper
import com.sportstracking.helmetly.utility.TinyDB

class TeamSelectionFragment : Fragment() {
    private lateinit var favoriteSelectionViewModel: FavoriteSelectionViewModel
    private lateinit var teamsRecyclerView: RecyclerView
    private lateinit var teamSelectionAdapter: TeamSelectionAdapter
    private lateinit var selectedSport: String
    private lateinit var selectedCountry: String
    private var _binding: FragmentTeamSelectionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteSelectionViewModel =
            ViewModelProvider(requireActivity()).get(FavoriteSelectionViewModel::class.java)
        selectedCountry = arguments?.getString(COUNTRY_ARG).toString()
        selectedSport = arguments?.getString(SPORT_ARG).toString()
        favoriteSelectionViewModel.getTeams(selectedSport, selectedCountry)
        activity?.invalidateOptionsMenu()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (context as AppCompatActivity).supportActionBar?.let {
            it.title = "Team Selection"
            it.show()
        }
        _binding = FragmentTeamSelectionBinding.inflate(inflater, container, false)
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        binding.apply {
            teamsRecyclerView = teamSelectionList
            selectionDone.setOnClickListener {
                if (favoriteSelectionViewModel.selectedTeams.value?.isEmpty() == false) {
                    saveSelectedTeamsToSharedPref()
                    startActivity(Intent(context, HomeActivity::class.java))
                    activity?.finish()
                    activity?.finishAffinity()
                } else {

                    Toast.makeText(
                        context,
                        "Please select atleast one team to proceed",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
            noTeamsText.text =
                getString(R.string.no_teams_available_text, selectedCountry, selectedSport)
            noTeamsButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        teamSelectionAdapter = TeamSelectionAdapter(favoriteSelectionViewModel)
        teamsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        teamsRecyclerView.adapter = teamSelectionAdapter

        favoriteSelectionViewModel.teamsData.observe(viewLifecycleOwner, {
            if (it.teams != null) {
                teamSelectionAdapter.setTeams(it.teams)
                teamSelectionAdapter.notifyDataSetChanged()
                showRecyclerViewAndRemoveMessages()
            } else {
                hideRecyclerViewAndShowNoTeamsMessage()
            }
        })
    }

    private fun showRecyclerViewAndRemoveMessages() {
        teamsRecyclerView.visibility = View.VISIBLE
        binding.noTeamsText.visibility = View.GONE
        binding.noTeamsButton.visibility = View.GONE
    }

    private fun hideRecyclerViewAndShowNoTeamsMessage() {
        teamsRecyclerView.visibility = View.GONE
        binding.noTeamsText.visibility = View.VISIBLE
        binding.noTeamsButton.visibility = View.VISIBLE
    }

    private fun saveSelectedTeamsToSharedPref() {
        // get the selected teams from the VM, and save it to the shared pref with a key specific to user (uid_SELECTED_TEAMS)
        val list = arrayListOf<Team>()
        favoriteSelectionViewModel.selectedTeams.value?.let { list.addAll(it) }
        TinyDB(context).putListObject(
            "${SharedPrefHelper.UID}_FAV_TEAMS",
            list as ArrayList<Any>
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.team_selection_menu, menu)

        val searchView = menu.findItem(R.id.team_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                try {
                    teamSelectionAdapter.filter.filter(newText)
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "No countries match your query",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return false
            }
        })
    }

    companion object {
        private const val SPORT_ARG = "sport"
        private const val COUNTRY_ARG = "country"
    }
}