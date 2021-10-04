package com.sportstracking.helmetly.ui.events

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sportstracking.helmetly.HomeActivity
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.data.TeamArray.Team
import com.sportstracking.helmetly.databinding.FragmentEventsBinding
import com.sportstracking.helmetly.utility.SharedPrefHelper
import com.sportstracking.helmetly.utility.TinyDB

class EventsFragment : Fragment() {

    private lateinit var eventsViewModel: EventsViewModel
    private var _binding: FragmentEventsBinding? = null
    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var favTeams: ArrayList<Team>
    private lateinit var pastEventsRecyclerView: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventsViewModel =
            ViewModelProvider(requireActivity()).get(EventsViewModel::class.java)

        favTeams = TinyDB(context).getListObject(
            "${SharedPrefHelper.UID}_FAV_TEAMS", Team::class.java
        ) as ArrayList<Team>

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        finishActivityOnBackPress()

        pastEventsRecyclerView = binding.pastEventsList

        eventsAdapter = EventsAdapter()
        pastEventsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        pastEventsRecyclerView.adapter = eventsAdapter

        binding.noEventsText.text = getString(R.string.no_events_text)
        eventsViewModel.pastEventsData.observe(viewLifecycleOwner, {
            if (it.results != null) {
                pastEventsRecyclerView.visibility = View.VISIBLE
                binding.noEventsText.visibility = View.GONE
                eventsAdapter.setEvents(it.results)
                eventsAdapter.notifyDataSetChanged()
            } else {
                hideRecyclerViewAndShowNoEventsMessage()
            }
        })

        return root
    }

    private fun finishActivityOnBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun hideRecyclerViewAndShowNoEventsMessage() {
        pastEventsRecyclerView.visibility = View.GONE
        binding.noEventsText.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        HomeActivity.lastVisitedFragment = "events"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.events_menu, menu)
        val spinner = menu.findItem(R.id.team_change).actionView as AppCompatSpinner
        val favTeamNames = mutableListOf<String>()
        favTeams.forEach {
            favTeamNames.add(it.strTeam)
        }
        val arrayAdapter =
            ArrayAdapter(context as Activity, R.layout.team_change_spinner_list_item, favTeamNames)
        arrayAdapter.setDropDownViewResource(R.layout.team_change_spinner_list_item_checked)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedTeam = favTeams.filter {
                    it.strTeam == favTeamNames[position]
                }[0]
                eventsViewModel.getPastEvents(selectedTeam)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}