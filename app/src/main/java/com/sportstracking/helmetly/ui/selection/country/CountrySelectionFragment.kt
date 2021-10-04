package com.sportstracking.helmetly.ui.selection.country

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.databinding.FragmentCountrySelectionBinding
import com.sportstracking.helmetly.ui.selection.FavoriteSelectionViewModel

class CountrySelectionFragment : Fragment() {
    private lateinit var favoriteSelectionViewModel: FavoriteSelectionViewModel
    private lateinit var countriesRecyclerView: RecyclerView
    private lateinit var countrySelectionAdapter: CountrySelectionAdapter
    private lateinit var selectedSport: String
    private var _binding: FragmentCountrySelectionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteSelectionViewModel =
            ViewModelProvider(requireActivity()).get(FavoriteSelectionViewModel::class.java)
        favoriteSelectionViewModel.getCountries()
        activity?.invalidateOptionsMenu()
        selectedSport = arguments?.getString(SPORT_ARG).toString()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity).supportActionBar?.let {
            it.title = "Country Selection"
            it.show()
        }

        _binding = FragmentCountrySelectionBinding.inflate(inflater, container, false)
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        binding.apply {
            countriesRecyclerView = countrySelectionList
        }
        countrySelectionAdapter = CountrySelectionAdapter(selectedSport)
        countriesRecyclerView.layoutManager = LinearLayoutManager(context)
        countriesRecyclerView.adapter = countrySelectionAdapter

        favoriteSelectionViewModel.countriesData.observe(viewLifecycleOwner, {
            countrySelectionAdapter.setCountries(it.countries)
            countrySelectionAdapter.notifyDataSetChanged()
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.country_selection_menu, menu)

        val searchView = menu.findItem(R.id.country_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                try {
                    countrySelectionAdapter.filter.filter(newText)
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
    }
}