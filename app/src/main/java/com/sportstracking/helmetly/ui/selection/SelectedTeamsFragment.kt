package com.sportstracking.helmetly.ui.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sportstracking.helmetly.databinding.FragmentSelectedTeamsBinding

class SelectedTeamsFragment : Fragment() {
    private lateinit var favoriteSelectionViewModel: FavoriteSelectionViewModel
    private lateinit var selectedTeamRecyclerView: RecyclerView
    private lateinit var selectedTeamsAdapter: SelectedTeamsAdapter
    private var _binding: FragmentSelectedTeamsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectedTeamsBinding.inflate(inflater, container, false)
        favoriteSelectionViewModel =
            ViewModelProvider(requireActivity()).get(FavoriteSelectionViewModel::class.java)
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        binding.apply {
            selectedTeamRecyclerView = selectedTeamsList
        }
        selectedTeamsAdapter = SelectedTeamsAdapter(favoriteSelectionViewModel)
        selectedTeamRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        selectedTeamRecyclerView.adapter = selectedTeamsAdapter

        favoriteSelectionViewModel.selectedTeams.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                hideRecyclerviewAndShowText()
            } else {
                selectedTeamRecyclerView.visibility = View.VISIBLE
                binding.noFavoriteText.visibility = View.GONE
                selectedTeamsAdapter.setTeams(it)
                selectedTeamsAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun hideRecyclerviewAndShowText() {
        selectedTeamRecyclerView.visibility = View.GONE
        binding.noFavoriteText.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}