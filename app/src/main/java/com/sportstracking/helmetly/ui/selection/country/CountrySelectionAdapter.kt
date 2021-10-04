package com.sportstracking.helmetly.ui.selection.country

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.data.CountryArray.Country
import java.util.*

class CountrySelectionAdapter(
    private val sport: String,
    private val countries: MutableList<Country> = mutableListOf()
) : RecyclerView.Adapter<CountrySelectionAdapter.CountryViewHolder>(), Filterable {

    private lateinit var context: Context
    private val countriesDataComplete: MutableList<Country> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.country_list_item, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.apply {
            countryName.text = countries[position].countryName
            itemView.setOnClickListener {
                val directions =
                    CountrySelectionFragmentDirections.actionNavigationCountrySelectionToNavigationTeamSelection(
                        countries[position].countryName,
                        sport
                    )
                hideKeyBoardWhileNaivgating(it)
                it.findNavController().navigate(directions)
            }
        }
    }

    private fun hideKeyBoardWhileNaivgating(itemView: View) {
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(itemView.windowToken, 0)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun setCountries(countries: List<Country>) {
        countriesDataComplete.clear()
        this.countries.clear()
        countriesDataComplete.addAll(countries)
        this.countries.addAll(countries)
    }


    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView = view.findViewById(R.id.country_name)
    }

    override fun getFilter(): Filter {
        val filter: Filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredCountriesData: ArrayList<Country> = ArrayList<Country>()
                if (constraint.isEmpty()) {
                    filteredCountriesData.addAll(countriesDataComplete)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (country in countriesDataComplete) {
                        if (country.countryName.toLowerCase()
                                .contains(filterPattern)
                        ) {
                            filteredCountriesData.add(country)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredCountriesData
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                countries.clear()
                countries.addAll(results.values as ArrayList<Country>)
                notifyDataSetChanged()
            }
        }
        return filter
    }
}