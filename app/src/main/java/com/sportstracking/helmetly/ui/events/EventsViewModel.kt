package com.sportstracking.helmetly.ui.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sportstracking.helmetly.data.News
import com.sportstracking.helmetly.data.Result
import com.sportstracking.helmetly.data.TeamArray.Team
import com.sportstracking.helmetly.network.NetworkService
import com.sportstracking.helmetly.network.viewmodelservice.SDBService
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {

    val pastEventsData: MutableLiveData<Result> = MutableLiveData()
    val newsData: MutableLiveData<MutableMap<Team, News.Article>> =
        MutableLiveData() // this contains all the favorite teams selected by user and their respective news stories

    fun getPastEvents(team: Team) {
        viewModelScope.launch {
            val service = NetworkService(URL).retrofit
            val sdbService: SDBService by lazy {
                service.create(SDBService::class.java)
            }
            pastEventsData.value =
                sdbService.getEventData("v1/json/1/eventslast.php?id=${team.idTeam}")
        }
    }

    fun getNewsData(favTeams: List<Team>) {
        val newsMapData: MutableMap<Team, News.Article> = mutableMapOf()
        viewModelScope.launch {
            val service = NetworkService(NEWS_URL).retrofit
            val sdbService: SDBService by lazy {
                service.create(SDBService::class.java)
            }
            favTeams.forEach { team ->
                sdbService.getTeamNews("everything?q=${team.strTeam} ${team.strSport}&apiKey=$API_KEY&sortBy=relevancy&pageSize=1").articles?.let { articles ->
                    if (articles.isNotEmpty()) {
                        articles[0].let { article ->
                            newsMapData.put(team, article)
                        }
                    }
                }
            }
            newsData.value = newsMapData
        }

    }


    companion object {
        private const val URL = "https://www.thesportsdb.com/api/"
        private const val NEWS_URL = "https://newsapi.org/v2/"
        private const val API_KEY = "481c44c24b1a457cad8454a005db6808"
    }


}