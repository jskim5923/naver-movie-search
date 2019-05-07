package com.example.movie_search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.R.id.message
import android.R
import android.view.inputmethod.EditorInfo


class MovieSearchViewModel : ViewModel() {
    private val TAG = MovieSearchViewModel::class.java.simpleName

    private val movieList: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }

    private val toastMessage = MutableLiveData<String>()

    private val hideKeyboard = MutableLiveData<Boolean>()

    val searchKeyword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getObservableMovieList(): LiveData<List<Movie>> {
        return movieList
    }

    fun searchButtonClick(keyword: String) {
        if (keyword.isEmpty()) {
            toastMessage.value = "please input keyword"
            return
        }
        getMovieList(keyword)
    }

    fun onEditorAction(view: TextView, actionId: Int?, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboard.value = true
            view.text.toString().let {
                if (it.isNotEmpty()) {
                    getMovieList(it)
                    return true
                }
            }
        }
        return false
    }

    //todo: rest api 통신 부분 repository 패키지로 이동
    fun getMovieList(keyword: String) {
        SearchApi.create().getMovieInfoList(
                BuildConfig.NAVER_API_CLIENT_ID,
                BuildConfig.NAVER_API_CLIENT_SECRET,
                keyword
        ).enqueue(object : Callback<SearchMovieResponse> {
            override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                toastMessage.value = t.message
            }

            override fun onResponse(
                    call: Call<SearchMovieResponse>,
                    response: Response<SearchMovieResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.items?.let {
                        movieList.postValue(it)
                    }
                } else {
                    toastMessage.value = "Network Error"
                }
            }
        })
    }

    fun toastMessage(): LiveData<String> {
        return toastMessage
    }

    fun hideKeyboard(): LiveData<Boolean> {
        return hideKeyboard
    }
}