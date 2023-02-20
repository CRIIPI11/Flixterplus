package com.codepath.articlesearch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var bylineTextView: TextView
    private lateinit var abstractTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        abstractTextView = findViewById(R.id.mediaAbstract)

        // TODO: Get the extra from the Intent
        val item = intent.getSerializableExtra(ITEM_EXTRA) as Items

        // TODO: Set the title, byline, and abstract information from the article
        titleTextView.text = item.title
        abstractTextView.text = item.desc

        // TODO: Load the media image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500"+item.poster_url)
            .into(mediaImageView)
    }
}