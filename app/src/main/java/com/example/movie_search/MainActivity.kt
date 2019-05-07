package com.example.movie_search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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

        mBinding.viewModel = viewModel

        viewModel.getObservableMovieList().observe(this, Observer<List<Movie>> {
            mMovieRecyclerViewAdapter.setMovieList(it!!)
        })

        viewModel.toastMessage().observe(this, Observer<String> {nullableMessage ->
            nullableMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.hideKeyboard().observe(this, Observer<Boolean> {nulableIsHide ->
            nulableIsHide?.let {
                if(it) {
                    hideKeyboard()
                }
            }
        })
    }

    private fun hideKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(et_search.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }
}
