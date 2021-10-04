package com.sportstracking.helmetly.ui.selection.sport

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sportstracking.helmetly.HomeActivity
import com.sportstracking.helmetly.NoNetworkActivity
import com.sportstracking.helmetly.data.TeamArray
import com.sportstracking.helmetly.databinding.FragmentSportSelectionBinding
import com.sportstracking.helmetly.network.NetworkConnectivityChecker
import com.sportstracking.helmetly.ui.selection.FavoriteSelectionViewModel
import com.sportstracking.helmetly.utility.SharedPrefHelper
import com.sportstracking.helmetly.utility.TinyDB


class SportSelectionFragment : Fragment() {

    private lateinit var favoriteSelectionViewModel: FavoriteSelectionViewModel
    private lateinit var sportsRecyclerView: RecyclerView
    private lateinit var favTeams: List<TeamArray.Team>

    private var fromHome: Boolean = false
    private var _binding: FragmentSportSelectionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteSelectionViewModel =
            ViewModelProvider(requireActivity()).get(FavoriteSelectionViewModel::class.java)
        if (!NetworkConnectivityChecker(requireContext()).checkForInternet()) {
            requireContext().startActivity(Intent(context, NoNetworkActivity::class.java))
            activity?.finish()
        } else {
            favoriteSelectionViewModel.getSports()
        }
        loadPreviouslySelectedTeamsIntoViewModel()
        fromHome = (activity as AppCompatActivity).intent.getBooleanExtra("fromHome", false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = "Sport Selection"
            it.show()
        }

        _binding = FragmentSportSelectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {
            sportsRecyclerView = sportSelectionList
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
        }

        handleBackPressed()

        val sportSelectionAdapter = SportSelectionAdapter()
        sportsRecyclerView.adapter = sportSelectionAdapter
        sportsRecyclerView.layoutManager = GridLayoutManager(context, 3)

        favoriteSelectionViewModel.sportsData.observe(viewLifecycleOwner, {
            sportSelectionAdapter.setSports(it.sports)
            sportSelectionAdapter.notifyItemRangeChanged(0, it.sports.size - 1)
        })

        return root
    }

    private fun handleBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun loadPreviouslySelectedTeamsIntoViewModel() {
        favTeams = TinyDB(context).getListObject(
            "${SharedPrefHelper.UID}_FAV_TEAMS",
            TeamArray.Team::class.java
        ) as ArrayList<TeamArray.Team>
        favoriteSelectionViewModel.selectedTeams.value = favTeams
    }

    private fun saveSelectedTeamsToSharedPref() {
        // get the selected teams from the VM, and save it to the shared pref with a key specific to user (uid_SELECTED_TEAMS)
        val list = arrayListOf<TeamArray.Team>()
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
}