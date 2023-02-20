package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import java.io.StringReader

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY

class MainActivity : AppCompatActivity() {
    private var items = mutableListOf<Items>()
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        itemsRecyclerView = findViewById(R.id.articles)
        val ItemAdapter = ArticleAdapter(this, items)
        itemsRecyclerView.adapter = ItemAdapter

        itemsRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            itemsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val client = AsyncHttpClient()
        client.get("https://api.themoviedb.org/3/tv/top_rated?api_key=${SEARCH_API_KEY}", object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    // Do something with the returned json (contains article information)
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    // Save the articles
                    parsedJson.results.let { list ->
                        items.addAll(list)

                        // Reload the screen
                        ItemAdapter.notifyDataSetChanged()}

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })

    }



}