package com.example.movie_search.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_search.R
import com.example.movie_search.base.BaseActivity
import com.example.movie_search.databinding.ActivityMainBinding
import com.example.movie_search.model.Movie
import com.example.movie_search.viewmodel.MovieSearchViewModel
import com.example.movie_search.viewmodel.MovieSearchViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private lateinit var mMovieRecyclerViewAdapter: MovieRecyclerViewAdapter

    private lateinit var viewModel: MovieSearchViewModel

    private val movieSearchViewModelFactory: MovieSearchViewModelFactory by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setBindingVariable()
        setObserver()
    }

    private fun init() {
        mMovieRecyclerViewAdapter = MovieRecyclerViewAdapter()
        dataBinding.recyclerView.adapter = mMovieRecyclerViewAdapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        viewModel = ViewModelProviders.of(
            this,
            movieSearchViewModelFactory
        ).get(MovieSearchViewModel::class.java)
    }

    private fun setBindingVariable() {
        dataBinding.viewModel = viewModel
    }

    private fun setObserver() {
        viewModel.movieList.observe(this, Observer<List<Movie>> {
            swipeRefreshLayout.visibility = if (it.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            swipeRefreshLayout.isRefreshing = false
            mMovieRecyclerViewAdapter.setMovieList(it!!)
        })

        viewModel.toastMessage.observe(this, Observer<String> { nullableMessage ->
            nullableMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.clickSearch.observe(this, Observer<Any> {
            dataBinding.etSearch.text.toString().also {
                getMovieList(it)
            }
        })

        viewModel.isHideKeyboard.observe(this, Observer<Boolean> { nullableIsHide ->
            nullableIsHide?.let {
                if (it) {
                    hideKeyboard()
                }
            }
        })

        viewModel.isRefresh.observe(this, Observer<Boolean> {
            getMovieList(et_search.text.toString())

        })
    }

    private fun getMovieList(keyword: String) {
        if (keyword.isEmpty()) {
            Toast.makeText(this, "please input keyword", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.getMovieList(keyword)
    }

    private fun hideKeyboard() {
        currentFocus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }
}
