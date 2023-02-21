package com.codepath.articlesearch

import android.R.attr.radius
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


const val ITEM_EXTRA = "ITEM_EXTRA"
private const val TAG = "ArticleAdapter"

class ItemAdapter(private val context: Context, private val items: List<Items>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val mediaImageView = itemView.findViewById<ImageView>(R.id.poster)
        private val titleTextView = itemView.findViewById<TextView>(R.id.name)
        private val abstractTextView = itemView.findViewById<TextView>(R.id.overview)
        private val dateTextView = itemView.findViewById<TextView>(R.id.date)
        private val polTextView = itemView.findViewById<TextView>(R.id.popularity)
        private val rateTextView = itemView.findViewById<TextView>(R.id.rating)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Items) {
            titleTextView.text = item.title
            abstractTextView.text = item.desc
            dateTextView.text = "Air Date\n"+item.date
            polTextView.text = "Popularity\n"+item.popularity
            rateTextView.text = "Rating\n"+item.rate


            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+item.poster_url)
                .transform(RoundedCorners(30))
                .into(mediaImageView)
        }

        override fun onClick(v: View?) {
            // TODO: Get selected article
            val item = items[absoluteAdapterPosition]

            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ITEM_EXTRA, item)
            context.startActivity(intent)
        }
    }


}