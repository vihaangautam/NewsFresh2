package com.example.newsfresh

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        private const val NEWS_URL = "https://saurav.tech/NewsAPI/top-headlines/category/technology/in.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        fetchData()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, NEWS_URL, null,
            { response ->
                try {
                    val newsJsonArray = response.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                            newsJsonObject.optString("title", "No Title"),
                            newsJsonObject.optString("author", "Unknown"),
                            newsJsonObject.optString("url", ""),
                            newsJsonObject.optString("urlToImage", "")
                        )
                        newsArray.add(news)
                    }
                    mAdapter.updateNews(newsArray)
                } catch (e: JSONException) {
                    showToast("Error parsing news data")
                }
            },
            { error -> handleVolleyError(error) }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun handleVolleyError(error: VolleyError) {
        val errorMessage = when (error.networkResponse?.statusCode) {
            404 -> "News source not found!"
            500 -> "Server error! Try again later."
            else -> "Failed to fetch data: ${error.localizedMessage}"
        }
        showToast(errorMessage)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
            .setInstantAppsEnabled(true)
            .setStartAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
