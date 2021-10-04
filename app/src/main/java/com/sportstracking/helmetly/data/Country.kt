package com.sportstracking.helmetly.data

import com.squareup.moshi.Json

data class CountryArray(val countries: List<Country>){
    data class Country(
        @Json(name = "name_en")
        val countryName: String
    )
}
