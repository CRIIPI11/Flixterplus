package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY

class MainActivity : AppCompatActivity() {
    private var items = mutableListOf<Items>()
    private var items2 = mutableListOf<Items>()
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var itemsRecyclerView2: RecyclerView
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        itemsRecyclerView = findViewById(R.id.items1)
        itemsRecyclerView2 = findViewById(R.id.items2)
        val ItemAdapter = ItemAdapter(this, items)
        val ItemAdapter2 = ItemAdapter(this, items2)
        itemsRecyclerView.adapter = ItemAdapter
        itemsRecyclerView2.adapter = ItemAdapter2

        itemsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            itemsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        itemsRecyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            itemsRecyclerView2.addItemDecoration(dividerItemDecoration)
        }

        val client = AsyncHttpClient()
        client.get("https://api.themoviedb.org/3/tv/popular?api_key=${SEARCH_API_KEY}", object : JsonHttpResponseHandler() {
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
                        items2.addAll(list)

                        // Reload the screen
                        ItemAdapter2.notifyDataSetChanged()}

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })


    }



}