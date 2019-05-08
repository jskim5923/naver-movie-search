package com.example.movie_search.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.movie_search.api.SearchMovieResponseError
import com.example.movie_search.model.Movie
import com.example.movie_search.repository.NetworkRepositoryImpl
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class MovieSearchViewModel(private val repository: NetworkRepositoryImpl) : ViewModel() {
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

    private fun getMovieList(keyword: String) {
        compositeDisposable.add(
            repository.getMovieList(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    movieList.value = response.items
                }, { errorResponse ->
                    if (errorResponse is HttpException) {
                        errorResponse.response().errorBody()?.run {
                            val responseError = Gson().fromJson(string(), SearchMovieResponseError::class.java)
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