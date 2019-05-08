package com.example.movie_search

import android.util.Log
import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("items") val items: List<Movie>
)


data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("subtitle") val subTitle: String,
    @SerializedName("director") var director: String,
    @SerializedName("actor") var actor: String,
    @SerializedName("userRating") val userRating: String
)

data class ResponseError(val errorMessage: String,
                         val errorCode:String)
