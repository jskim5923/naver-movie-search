package com.example.movie_search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class MovieSearchViewModel : ViewModel() {
    private val TAG = MovieSearchViewModel::class.java.simpleName

    private val movieList: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }

    private val toastMessage = MutableLiveData<String>()

    private val hideKeyboard = MutableLiveData<Boolean>()

    private val compositeDisposable = CompositeDisposable()

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
        compositeDisposable.add(
            SearchApi.create().getMovieInfoList(
                BuildConfig.NAVER_API_CLIENT_ID,
                BuildConfig.NAVER_API_CLIENT_SECRET,
                keyword
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    movieList.value = response.items
                }, { errorResponse ->
                    if (errorResponse is HttpException) {
                        errorResponse.response().errorBody()?.run {
                            val responseError = Gson().fromJson(string(), ResponseError::class.java)
                            toastMessage.value = responseError.errorMessage
                        }
                    }
                })
        )
    }

    fun toastMessage(): LiveData<String> {
        return toastMessage
    }

    fun hideKeyboard(): LiveData<Boolean> {
        return hideKeyboard
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}