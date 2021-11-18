package com.sportstracking.helmetly.ui.selection

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.ads.Interstitial
import com.sportstracking.helmetly.data.TeamArray
import com.sportstracking.helmetly.databinding.ActivityFavoriteSelectionBinding
import com.sportstracking.helmetly.ui.favorites.FavoritesFragment
import com.sportstracking.helmetly.utility.SharedPrefHelper
import com.sportstracking.helmetly.utility.TinyDB

/**
 * This class gets the data and populates the recycler view for the sport selection
 */
class FavoriteSelectionActivity : AppCompatActivity() {
    private lateinit var favoriteSelectionViewModel: FavoriteSelectionViewModel
    private lateinit var binding: ActivityFavoriteSelectionBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (shouldShowInterstitial()) showInterstitial()

        favoriteSelectionViewModel =
            ViewModelProvider(this).get(FavoriteSelectionViewModel::class.java)
        binding = ActivityFavoriteSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_favorite_selection)

        setupActionBarWithNavController(navController)

    }

    private fun shouldShowInterstitial(): Boolean {
        val tinyDB = TinyDB(this)
        val favTeams =
            tinyDB.getListObject("${SharedPrefHelper.UID}_FAV_TEAMS", TeamArray.Team::class.java)
        var timesVisited = tinyDB.getLong("visitedFavorite")
        if (timesVisited == 48L) timesVisited = 0L
        tinyDB.putLong("visitedFavorite", timesVisited + 1)
        return favTeams.isNotEmpty() && timesVisited % 3 == 0L
    }

    private fun showInterstitial() {
        Interstitial(this).loadAdAndShow()
    }

    override fun onSupportNavigateUp(): Boolean {
        val imm: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            binding.root.windowToken,
            0
        ) // hide keyboard when navigating to the next/previous screen
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}