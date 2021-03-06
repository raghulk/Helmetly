package com.sportstracking.helmetly

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sportstracking.helmetly.databinding.ActivityNoNetworkBinding
import com.sportstracking.helmetly.network.NetworkConnectivityChecker
import com.sportstracking.helmetly.utility.SharedPrefHelper

class NoNetworkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoNetworkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.retryButton.setOnClickListener {
            if (NetworkConnectivityChecker(this).checkForInternet()) {
                checkIfIntroCompleted()
                finish()
            }
        }
    }

    private fun checkIfIntroCompleted() {
        val sharedPrefHelper = SharedPrefHelper(this).sharedPref
        sharedPrefHelper.let {
            if (!it.contains(getString(R.string.onBoarding_done))) {
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
    }
}