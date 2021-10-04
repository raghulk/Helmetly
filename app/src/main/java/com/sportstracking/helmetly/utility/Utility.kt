package com.sportstracking.helmetly.utility

import android.content.Context
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager

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
}