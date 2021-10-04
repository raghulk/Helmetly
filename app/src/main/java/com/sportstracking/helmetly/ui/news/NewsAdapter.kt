package com.sportstracking.helmetly.ui.news

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sportstracking.helmetly.R
import com.sportstracking.helmetly.data.News.Article
import com.sportstracking.helmetly.data.TeamArray.Team
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.model.MyStory
import omari.hamza.storyview.utils.StoryViewHeaderInfo
import java.text.SimpleDateFormat
import java.util.*


class NewsAdapter(
    private var news: LinkedHashMap<Team, Article> = linkedMapOf()
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.news_list_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val team = news.keys.toTypedArray()[position]
        val stories: ArrayList<MyStory> = arrayListOf()
        val headerInfoArrayList: ArrayList<StoryViewHeaderInfo> = arrayListOf()
        val article = news.get(team)

        stories.add(
            MyStory(news[team]?.urlToImage,
                article?.publishedAt?.let { getFormattedTime(it) })
        )
        headerInfoArrayList.add(
            StoryViewHeaderInfo(
                team.strTeam, article?.title?.substringBeforeLast(" - "), team.strTeamBadge
            )
        )
        holder.apply {
            setImage(team.strTeamBadge, teamLogo, team.strSport.toString())
            teamLogo.setOnClickListener {
                StoryView.Builder((context as AppCompatActivity).supportFragmentManager)
                    .setStoriesList(stories)
                    .setStoryDuration(10000)
                    .setHeadingInfoList(headerInfoArrayList)
                    .build()
                    .show()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFormattedTime(date: String): Date {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return try {
            inputFormat.parse(date)
        } catch (e: Exception) {
            Date()
        }
    }


    private fun setImage(imgUrl: String?, imageView: ImageView, sport: String) {
        var imageURL = imgUrl
        if (imgUrl.isNullOrEmpty()) {
            imageURL = "https://source.unsplash.com/200x200/?$sport&${Math.random()}"
        }
        Glide.with(context)
            .asDrawable()
            .load(imageURL)
            .apply(RequestOptions().override(120, 120))
            .into(imageView)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun setNews(news: MutableMap<Team, Article>) {
        this.news.clear()
        val linkedHashMapNews = linkedMapOf<Team, Article>()
        news.forEach {
            linkedHashMapNews.put(it.key, it.value)
        }
        this.news = linkedHashMapNews
    }


    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamLogo: ImageView = view.findViewById(R.id.team_logo)
    }
}