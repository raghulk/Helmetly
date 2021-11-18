package com.sportstracking.helmetly.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class Interstitial(val context: Context) {
    var mInterstitialAd: InterstitialAd? = null

    init {
        loadAd()
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("Interstitial", "ad failed to load")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("Interstitial", "ad loaded")
                    mInterstitialAd = interstitialAd
//                    showAdIfAvailable()
                }
            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {}

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d("Interstitial", "ad error")
            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
            }
        }
    }

    fun loadAdAndShow() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("Interstitial", "ad failed to load")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("Interstitial", "ad loaded")
                    mInterstitialAd = interstitialAd
                    showAdIfAvailable()
                }
            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {}

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d("Interstitial", "ad error")
            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
            }
        }
    }

    fun showAdIfAvailable() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(context as Activity)
        }
    }
}