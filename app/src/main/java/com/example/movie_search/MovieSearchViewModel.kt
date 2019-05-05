package com.example.movie_search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieSearchViewModel : ViewModel() {
    private val TAG = MovieSearchViewModel::class.java.simpleName

    private val movieList: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }

    private val restApiFailedMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getObservableMovieList(): LiveData<List<Movie>> {
        return movieList
    }

    fun getObservableFailedMessage(): MutableLiveData<String> {
        return restApiFailedMessage
    }

    //todo: rest api 통신 부분 repository 패키지로 이동
    fun getMovieList(keyword: String) {
        SearchApi.create().getMovieInfoList(
            BuildConfig.NAVER_API_CLIENT_ID,
            BuildConfig.NAVER_API_CLIENT_SECRET,
            keyword
        ).enqueue(object : Callback<SearchMovieResponse> {
            override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                restApiFailedMessage.value = t.message
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
                    restApiFailedMessage.value = "Network Error"
                }
            }
        })
    }

}