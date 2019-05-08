package com.example.movie_search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_search.base.BaseRecyclerViewAdapter
import com.example.movie_search.databinding.ItemMovieBinding
import com.example.movie_search.model.Movie

class MovieRecyclerViewAdapter : BaseRecyclerViewAdapter<Movie, MovieRecyclerViewAdapter.ViewHolder>() {
    fun setMovieList(movieList: List<Movie>) {
        super.updateItem(movieList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHoler: ViewHolder, position: Int) {
        dataList?.let {
            viewHoler.bind(it[position])
        }
    }

    inner class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }
}