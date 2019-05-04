package com.example.movie_search

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

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

            getMovieList(keyword)
        }
    }

    private fun hideKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(et_search.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    private fun getMovieList(keyword: String) {
        SearchApi.create().getMovieInfoList(
            BuildConfig.NAVER_API_CLIENT_ID,
            BuildConfig.NAVER_API_CLIENT_SECRET,
            keyword
        ).enqueue(object : Callback<SearchMovieResponse> {
            override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<SearchMovieResponse>,
                response: Response<SearchMovieResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.items?.let {
                        setRecyclerView(it)
                    }
                }
            }
        })
    }

    private fun setRecyclerView(movieList: List<Movie>) {
        mBinding.recyclerView.adapter = MovieRecyclerViewAdapter(movieList)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}
