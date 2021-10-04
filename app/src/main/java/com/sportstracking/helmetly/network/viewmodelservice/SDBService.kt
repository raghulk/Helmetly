package com.sportstracking.helmetly.network.viewmodelservice

import com.sportstracking.helmetly.data.*
import retrofit2.http.GET
import retrofit2.http.Url

interface SDBService {
    @GET
    suspend fun getSportData(@Url url: String): SportsArray
    @GET
    suspend fun getCountryData(@Url url: String): CountryArray
    @GET
    suspend fun getTeamData(@Url url: String): TeamArray
    @GET
    suspend fun getEventData(@Url url: String): Result
    @GET
    suspend fun getTeamNews(@Url url: String): News
}
