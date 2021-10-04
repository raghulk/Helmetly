package com.sportstracking.helmetly.network

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import android.net.NetworkInfo

import androidx.core.content.ContextCompat.getSystemService

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat


class NetworkService(url: String) {



    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(url)
        .build()

}