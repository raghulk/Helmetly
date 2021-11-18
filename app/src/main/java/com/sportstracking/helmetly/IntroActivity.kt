package com.sportstracking.helmetly

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.model.SliderPage
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.MobileAds
import com.sportstracking.helmetly.ads.Interstitial
import com.sportstracking.helmetly.utility.SharedPrefHelper


class IntroActivity : AppIntro() {
    private lateinit var ad: Interstitial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)
        ad = Interstitial(this)

        addPage1()
        addPage2()
        addPage3()
        addPage4()

        setBarColor(ContextCompat.getColor(applicationContext, R.color.primary_color))
        setSeparatorColor(ContextCompat.getColor(applicationContext, R.color.primary_color))
    }

    private fun addPage1() {
        val sliderPage = SliderPage()
        sliderPage.title = "Welcome to Helmetly!"
        sliderPage.description =
            "Track and manage your favorite sport team news, events, and history"
        sliderPage.imageDrawable = R.drawable.app_logo_walkthorugh
        sliderPage.backgroundColor =
            ContextCompat.getColor(applicationContext, R.color.primary_color_variant)
        addSlide(AppIntroFragment.newInstance(sliderPage))
    }

    private fun addPage2() {
        val sliderPage = SliderPage()
        sliderPage.title = "Pick any sport, country or team we have it all!"
        sliderPage.description = "Find and start following a range of teams across the globe"
        sliderPage.imageDrawable = R.drawable.multi_sport
        sliderPage.backgroundColor =
            ContextCompat.getColor(applicationContext, R.color.primary_color_variant)
        addSlide(AppIntroFragment.newInstance(sliderPage))
    }

    private fun addPage3() {
        val sliderPage = SliderPage()
        sliderPage.title = "We have got you covered with stories!"
        sliderPage.description =
            "Stay updated with your favorite team's trending news with our Highlights sections"
        sliderPage.imageDrawable = R.drawable.news
        sliderPage.backgroundColor =
            ContextCompat.getColor(applicationContext, R.color.primary_color_variant)
        addSlide(AppIntroFragment.newInstance(sliderPage))
    }

    private fun addPage4() {
        val sliderPage = SliderPage()
        sliderPage.title = "Share your thoughts and feedback!"
        sliderPage.description =
            "Go to the playstore to provide your feedback. All suggestions and feature requests are welcome. Thank you!"
        sliderPage.imageDrawable = R.drawable.play_store
        sliderPage.backgroundColor =
            ContextCompat.getColor(applicationContext, R.color.primary_color_variant)
        addSlide(AppIntroFragment.newInstance(sliderPage))
    }


    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        markIntroAsComplete()
        displayAdAndTakeUserToSignIn()
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        markIntroAsComplete()
        displayAdAndTakeUserToSignIn()
        finish()
    }

    private fun markIntroAsComplete() {
        SharedPrefHelper(this).insertBoolean(getString(R.string.onBoarding_done), true)
    }

    private fun displayAdAndTakeUserToSignIn() {
        val ad = ad.mInterstitialAd
        if (ad != null) {
            ad.show(this)
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    val intent = Intent(this@IntroActivity, SignInActivity::class.java)
                    startActivity(intent)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    Log.d("Interstitial", "ad error")
                    val intent = Intent(this@IntroActivity, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

}
