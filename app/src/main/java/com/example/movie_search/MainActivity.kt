package com.example.movie_search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.movie_search.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mMovieRecyclerViewAdapter: MovieRecyclerViewAdapter

    private lateinit var viewModel: MovieSearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        mMovieRecyclerViewAdapter = MovieRecyclerViewAdapter()
        mBinding.recyclerView.adapter = mMovieRecyclerViewAdapter
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        viewModel = ViewModelProviders.of(this).get(MovieSearchViewModel::class.java)

        et_search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btn_search.performClick()
                hideKeyboard()
            }
            false
        }

        btn_search.setOnClickListener {
            val keyword = et_search.text.toString()
            if (keyword.isEmpty()) {
                Toast.makeText(this@MainActivity, "please input keyword", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getMovieList(keyword)
        }

        val viewModel = ViewModelProviders.of(this@MainActivity).get(MovieSearchViewModel::class.java)

        viewModel.getObservableMovieList().observe(this, Observer<List<Movie>> {
            mMovieRecyclerViewAdapter.setMovieList(it!!)
        })
        viewModel.getObservableFailedMessage().observe(this, Observer<String> {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun hideKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(et_search.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }
}
