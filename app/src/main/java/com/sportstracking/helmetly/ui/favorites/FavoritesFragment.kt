package com.sportstracking.helmetly.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sportstracking.helmetly.HomeActivity
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.databinding.FragmentFavoritesBinding
import com.sportstracking.helmetly.ui.selection.FavoriteSelectionActivity

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        val intent = Intent(context, FavoriteSelectionActivity::class.java)
        intent.putExtra("fromHome", true)
        startActivity(intent)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        when (HomeActivity.lastVisitedFragment) {
            "events" -> findNavController().navigate(R.id.navigation_events)
            "more" -> findNavController().navigate(R.id.navigation_more)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}