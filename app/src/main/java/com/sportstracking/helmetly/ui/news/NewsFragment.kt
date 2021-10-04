package com.sportstracking.helmetly.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sportstracking.helmetly.data.TeamArray.Team
import com.sportstracking.helmetly.databinding.FragmentNewsBinding
import com.sportstracking.helmetly.ui.events.EventsViewModel
import com.sportstracking.helmetly.utility.SharedPrefHelper
import com.sportstracking.helmetly.utility.TinyDB

class NewsFragment : Fragment() {

    private lateinit var eventsViewModel: EventsViewModel
    private var _binding: FragmentNewsBinding? = null
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var teamNewsRecyclerView: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventsViewModel =
            ViewModelProvider(requireActivity()).get(EventsViewModel::class.java)
        val favTeams = TinyDB(context).getListObject(
            "${SharedPrefHelper.UID}_FAV_TEAMS", Team::class.java
        ) as ArrayList<Team>
        eventsViewModel.getNewsData(favTeams)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {
            teamNewsRecyclerView = teamNewsList
        }
        newsAdapter = NewsAdapter()
        teamNewsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        teamNewsRecyclerView.adapter = newsAdapter


        eventsViewModel.newsData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                teamNewsRecyclerView.visibility = View.VISIBLE
                binding.noNewsText.visibility = View.GONE
                newsAdapter.setNews(it)
                newsAdapter.notifyDataSetChanged()
            } else {
                hideRecyclerViewAndShowNoNewsMessage()
            }
        })
        return root
    }

    private fun hideRecyclerViewAndShowNoNewsMessage() {
        teamNewsRecyclerView.visibility = View.GONE
        binding.noNewsText.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}