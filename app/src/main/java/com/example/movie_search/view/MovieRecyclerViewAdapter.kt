package com.example.movie_search.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movie_search.databinding.ItemMovieBinding
import com.example.movie_search.model.Movie

class MovieRecyclerViewAdapter :
    RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    var dataList: List<Movie> = arrayListOf()

    fun setMovieList(movieList: List<Movie>) {
        dataList = movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(viewHoler: ViewHolder, position: Int) {
        viewHoler.bind(dataList[position])
    }

    inner class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }


}