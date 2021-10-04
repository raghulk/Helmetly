package com.sportstracking.helmetly.ui.selection

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sportstracking.helmetly.data.CountryArray
import com.sportstracking.helmetly.data.SportsArray
import com.sportstracking.helmetly.data.TeamArray
import com.sportstracking.helmetly.data.TeamArray.Team
import com.sportstracking.helmetly.network.NetworkService
import com.sportstracking.helmetly.network.viewmodelservice.SDBService
import com.sportstracking.helmetly.ui.selection.team.TeamSelectionAdapter.SubscribeToChanges
import kotlinx.coroutines.launch

class FavoriteSelectionViewModel : ViewModel(), SubscribeToChanges {

    val sportsData: MutableLiveData<SportsArray> = MutableLiveData()
    val countriesData: MutableLiveData<CountryArray> = MutableLiveData()
    val teamsData: MutableLiveData<TeamArray> = MutableLiveData()
    val selectedTeams: MutableLiveData<List<Team>> = MutableLiveData()

    fun getSports() {
        viewModelScope.launch {
            val service = NetworkService(URL).retrofit
            val sdbService: SDBService by lazy {
                service.create(SDBService::class.java)
            }
            sportsData.value = sdbService.getSportData("v1/json/1/all_sports.php")
        }
    }

    fun getCountries() {
        viewModelScope.launch {
            val service = NetworkService(URL).retrofit
            val sdbService: SDBService by lazy {
                service.create(SDBService::class.java)
            }
            countriesData.value = sdbService.getCountryData("v1/json/1/all_countries.php")
        }
    }

    fun getTeams(selectedSport: String, selectedCountry: String) {
        viewModelScope.launch {
            val service = NetworkService(URL).retrofit
            val sdbService: SDBService by lazy {
                service.create(SDBService::class.java)
            }
            teamsData.value = sdbService.getTeamData("v1/json/1/search_all_teams.php?s=$selectedSport&c=$selectedCountry")
        }
    }

    companion object {
        private const val URL = "https://www.thesportsdb.com/api/"
    }

    override fun add(team: Team) {
        val teamList: MutableSet<Team> = mutableSetOf()
        selectedTeams.value?.toMutableSet()?.let { teamList.addAll(it) }
        teamList.add(team)
        selectedTeams.value = teamList.toList()
    }

    override fun remove(team: Team) {
        val teamList: MutableList<Team> = mutableListOf()
        selectedTeams.value?.let { teamList.addAll(it) }
        val newTeamList = selectedTeams.value?.filter {
            it.idTeam != team.idTeam
        }
        selectedTeams.value = newTeamList
    }
}
