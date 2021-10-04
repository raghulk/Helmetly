package com.sportstracking.helmetly.utility

import android.content.Context
import com.sportstracking.helmetly.R

class SharedPrefHelper(context: Context) {

    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.sharedPrefFileName),
        Context.MODE_PRIVATE
    )


    fun insertBoolean(key: String, value: Boolean) {
        with(sharedPref.edit()) {
            this?.putBoolean(key, value)
            this.apply()
        }
    }

    fun insertString(key: String, value: String) {
        with(sharedPref.edit()) {
            this?.putString(key, value)
            this.apply()
        }
    }

    fun insertInt(key: String, value: Int) {
        with(sharedPref.edit()) {
            this?.putInt(key, value)
            this.apply()
        }
    }

    companion object {
        var UID = "1234"
    }

}