package com.example.movie_search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class MovieRecyclerViewAdapter(var dataList: List<Movie>) :
    RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(viewHoler: MovieRecyclerViewAdapter.ViewHolder, position: Int) {
        dataList[position].let {
            with(viewHoler) {
                title.text = "${StringUtil.stripHtmlTag(it.title)}(${it.subTitle})"
                rating.text = it.userRating
                director.text = itemView.context.resources.getString(R.string.director,StringUtil.subtractEndSymbol(it.director, "|"))
                actor.text = itemView.context.resources.getString(R.string.actor, StringUtil.subtractEndSymbol(it.actor, "|"))

                Glide.with(itemView.context)
                    .load(it.imageUrl)
                    .placeholder(R.drawable.poster_placeholder)
                    .centerCrop()
                    .into(posterImage)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val posterImage = itemView.findViewById<ImageView>(R.id.iv_poster)
        val rating = itemView.findViewById<TextView>(R.id.tv_userRating)
        val director = itemView.findViewById<TextView>(R.id.tv_director)
        val actor = itemView.findViewById<TextView>(R.id.tv_actor)

    }


}