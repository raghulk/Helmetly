package com.sportstracking.helmetly.utility

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Utility {

    fun getScreenSize(context: Context): List<String> {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        return listOf(width.toString(), height.toString())
    }

    @SuppressLint("SimpleDateFormat")
    fun getDaysBetweenDateAndToday(dateString: String): Long {
        val date = SimpleDateFormat("EEE MMM d HH:mm:ss zz yyyy").parse(dateString)
        return TimeUnit.DAYS.convert(Date().time - date.time, TimeUnit.MILLISECONDS)
    }
}