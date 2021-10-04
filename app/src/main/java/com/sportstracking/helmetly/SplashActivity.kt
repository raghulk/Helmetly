package com.sportstracking.helmetly

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sportstracking.helmetly.network.NetworkConnectivityChecker
import com.sportstracking.helmetly.utility.SharedPrefHelper

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIfInternetIsAvailable()
        finish()
    }

    private fun checkIfInternetIsAvailable() {
        if (!NetworkConnectivityChecker(this).checkForInternet()){
            startActivity(Intent(this, NoNetworkActivity::class.java))
        }
        else {
            checkIfIntroCompleted()
        }
    }

    private fun checkIfIntroCompleted() {
       val sharedPrefHelper = SharedPrefHelper(this).sharedPref
        sharedPrefHelper.let {
            if (!it.contains(getString(R.string.onBoarding_done))) {
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
    }
}