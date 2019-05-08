package com.example.movie_search.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.movie_search.R
import com.example.movie_search.base.BaseActivity
import com.example.movie_search.databinding.ActivityMainBinding
import com.example.movie_search.model.Movie
import com.example.movie_search.viewmodel.MovieSearchViewModel
import com.example.movie_search.viewmodel.MovieSearchViewModelFactory
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
            mMovieRecyclerViewAdapter.setMovieList(it!!)
        })

        viewModel.toastMessage.observe(this, Observer<String> { nullableMessage ->
            nullableMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.clickSearch.observe(this, Observer<Any> {
            dataBinding.etSearch.text.toString().also {
                if (it.isEmpty()) {
                    Toast.makeText(this, "please input keyword", Toast.LENGTH_SHORT).show()
                    return@Observer
                }
                viewModel.getMovieList(it)
            }
        })

        viewModel.isHideKeyboard.observe(this, Observer<Boolean> { nullableIsHide ->
            nullableIsHide?.let {
                if (it) {
                    hideKeyboard()
                }
            }
        })
    }

    private fun hideKeyboard() {
        currentFocus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }
}
