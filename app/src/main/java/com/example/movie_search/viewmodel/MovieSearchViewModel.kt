package com.example.movie_search.viewmodel

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movie_search.api.SearchMovieResponseError
import com.example.movie_search.model.Movie
import com.example.movie_search.repository.NetworkRepositoryImpl
import com.example.movie_search.util.SingleLiveEvent
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class MovieSearchViewModel(private val repository: NetworkRepositoryImpl) : DisposableViewModel() {
    private val TAG = MovieSearchViewModel::class.java.simpleName

    private val _movieList = MutableLiveData<List<Movie>>()

    private val _toastMessage = MutableLiveData<String>()

    private val _isHideKeyboard = SingleLiveEvent<Boolean>()

    private val _clickSearch = SingleLiveEvent<Any>()

    private val _isRefresh = SingleLiveEvent<Boolean>()

    val searchKeyword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val movieList: LiveData<List<Movie>>
        get() = _movieList

    val toastMessage: LiveData<String>
        get() = _toastMessage

    val isHideKeyboard: LiveData<Boolean>
        get() = _isHideKeyboard

    val clickSearch: LiveData<Any>
        get() = _clickSearch

    val isRefresh: LiveData<Boolean>
        get() = _isRefresh

    fun searchButtonClick() {
        _clickSearch.call()
    }

    fun swipeRefresh() {
        _isRefresh.call()
    }

    fun onEditorAction(view: TextView, actionId: Int?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            _isHideKeyboard.value = true
            view.text.toString().let {
                if (it.isNotEmpty()) {
                    getMovieList(it)
                    return true
                }
            }
        }
        return false
    }

    fun getMovieList(keyword: String) {
        addDisposable(
            repository.getMovieList(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _movieList.value = response.items
                }, { errorResponse ->
                    if (errorResponse is HttpException) {
                        errorResponse.response().errorBody()?.run {
                            val responseError = Gson().fromJson(string(), SearchMovieResponseError::class.java)
                            _toastMessage.value = responseError.errorMessage
                        }
                    }
                })
        )
    }
}