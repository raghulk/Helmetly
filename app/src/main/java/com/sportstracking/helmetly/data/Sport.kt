package com.sportstracking.helmetly.data

data class SportsArray(
    val sports: List<Sport>
) {
    data class Sport(
        val idSport: Int,
        val strSport: String,
        val strFormat: String,
        val strSportThumb: String,
        val strSportIconGreen: String,
        val strSportDescription: String
    )
}
