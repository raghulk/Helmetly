package com.sportstracking.helmetly.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sportstracking.helmetly.HomeActivity
import com.sportstracking.helmetly.IntroActivity
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.SignInActivity
import com.sportstracking.helmetly.databinding.FragmentMoreBinding
import com.sportstracking.helmetly.utility.SharedPrefHelper

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {
            val sharedPref = SharedPrefHelper(requireContext()).sharedPref
            setImage(
                sharedPref.getString(
                    getString(R.string.sign_in_avatar),
                    "https://source.unsplash.com/200x200/?person&${Math.random()}"
                ), userImage
            )
            userEmail.text =
                sharedPref.getString(getString(R.string.sign_in_email), "jdoe@gmail.com")
            userName.text = sharedPref.getString(getString(R.string.sign_in_name), "John Doe")
        }
        setupButtons()

        HomeActivity.lastVisitedFragment = "more"

        return root
    }

    private fun setImage(imgUrl: String?, imageView: ImageView) {
        var imageURL = imgUrl
        if (imgUrl.isNullOrEmpty()) {
            imageURL = "https://source.unsplash.com/200x200/?person&${Math.random()}"
        }
        Glide.with(requireContext())
            .asDrawable()
            .load(imageURL)
            .apply(RequestOptions().transform(RoundedCorners(8)).override(120, 120))
            .into(imageView)
    }

    private fun setupButtons() {
        setupAboutDeveloper()
        setupContributeProject()
        setupWalkthrough()
        setupLogout()
    }

    private fun setupAboutDeveloper() {
        binding.aboutDeveloper.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://linkedin.com/in/raghul-krishnan"))
            startActivity(browserIntent)
        }
    }

    private fun setupContributeProject() {
        binding.contributeProject.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/raghulk/Helmetly")
            )
            startActivity(browserIntent)
        }
    }

    private fun setupWalkthrough() {
        binding.appWalkthrough.setOnClickListener {
            startActivity(Intent(context, IntroActivity::class.java))
        }
    }

    private fun setupLogout() {
        binding.logoutButton.setOnClickListener {
            val intent = Intent(context, SignInActivity::class.java)
            intent.putExtra(SignInActivity.SIGN_OUT, true)
            startActivity(intent)
        }
    }

    override fun onResume() {
        HomeActivity.lastVisitedFragment = "more"
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}